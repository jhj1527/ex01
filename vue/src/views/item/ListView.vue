<script>
  import { commonApi } from '@/service/common';
  import { useStore } from '@/stores/store';
  import { mapState } from 'pinia';

  export default {
    name: 'ListView',
    data() {
      return {
        numbers: [],
        result: [],
        page: {}, 
        param: {
          pageNum: 1,
          amount: 10,
          type: "",
          keyword: "",
        },
      };
    },
    mounted() {
      this.getList();
    },
    computed: {
      ...mapState(useStore, ['member']),
      priceFormat() {
        return price => price ? price.toLocaleString() : '';
      },
    },  
    methods: {
      async getList() {
        try {
          const res = await commonApi('/api/item/list', "get", this.param);

          console.log(res.data.list);

          this.result = res.data.list;
          this.page = res.data.page;
          this.param = res.data.page.criteriaDto;

          this.numbers = [];

          for (let i = this.page.startPage; i <= this.page.endPage; i++) {
            this.numbers.push(i);
          }

        } catch (e) {
          console.log('Error fetching item list:', e);
        }
      },
      get(ino) {
        this.$router.push("/item/get/" + ino);
      },
      async getImage(filePath, fileName, i) {
        try {
          const params = {
            filePath: filePath,
            fileName: fileName
          };
          const res = await commonApi("/api/file/getFile", "GET", params);

          console.log(res.data);

          const url = URL.createObjectURL(res.data);
          this.$refs.mainImage[0].src = url;

        } catch (e) {
          console.log(e);
        }
      },
      pageClick(pageNum){
        this.param.pageNum = pageNum;
        this.getList();
      },
      search(){
        this.param.pageNum = 1;
        this.getList();
      },
      orderList(type) {
        this.param.pageNum = 1;
        this.param.type = type; 
        this.param.keyword = "";
        
        this.getList();
      },
      insert(){
        this.$router.push("/item/insert");
      },
    },
  };
</script>
<template>
  <div class="container">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <div>
        <span>총 <span style="color: #e74c3c;" v-html="priceFormat(page.total)"></span>건</span>
      </div>
      <div class="button-group">
        <button 
        v-if="member.id !== null && member.id.startsWith('admin')" 
        @click="insert" 
        class="btn btn-primary me-2"> 
        insert
      </button>
      </div>
      <div class="d-flex align-items-center justify-content-center flex-grow-1">
        <select v-model="param.type" class="form-select me-2" style="width: 120px;">
          <option value="">전체</option>
          <option value="fruit">과일</option>
          <option value="vegetable">채소</option>
        </select>
        <input type="text" v-model="param.keyword" @keyup.enter="search" class="form-control me-2" placeholder="상품명 검색" style="width: 300px;">
        <button @click="search" class="btn btn-primary">검색</button>
      </div>

      <div class="d-flex align-items-center" style="font-size: 0.9rem;">
        <p @click="orderList('newItem')" class="fw-bold me-3 sort-link" active-class="active" style="cursor: pointer;">신상품순</p>
        <p @click="orderList('lowPrice')" class="text-secondary me-3 sort-link" active-class="active" style="cursor: pointer;">낮은가격순</p>
        <p @click="orderList('highPrice')" class="text-secondary sort-link" active-class="active" style="cursor: pointer;">높은가격순</p>
      </div>
    </div>
    <hr>
    <!-- Section-->
    <section class="py-5">
      <div class="container px-4 px-lg-5 mt-5">
        <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4">
          
          <div v-for="(item, i) in result" :key="item.ino" class="col mb-5">
            <div class="card h-100">
              <!-- Sale badge-->
              <div v-if="item.discount > 0" class="badge bg-dark text-white position-absolute" style="top: 0.5rem; right: 0.5rem">Sale
              </div>
              <!-- Product image-->
              <img 
                v-if="item.attachList.length > 0 && item.attachList[0].fileType"
                :src="`http://localhost:8081/api/file/getFile?filePath=${item.attachList[0].filePath}&fileName=${item.attachList[0].fileName}`"
                ref="mainImage" 
                class="card-img-top"
              >
              <img v-else class="card-img-top" src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" alt="..." />
              
              <!-- Product details-->
              <div class="card-body p-4">
                <div class="text-center">
                  <!-- Product name-->
                  <h5 class="fw-bolder">{{ item.name }}</h5>
                  <!-- Product reviews-->
                  <div class="d-flex justify-content-center small text-warning mb-2">
                    <i class="bi bi-star-fill"></i>
                    <i class="bi bi-star-fill"></i>
                    <i class="bi bi-star-fill"></i>
                    <i class="bi bi-star-fill"></i>
                    <i class="bi bi-star-fill"></i>
                  </div>
                  <!-- Product price-->
                   <div v-if="item.discount > 0">
                     <span class="text-muted text-decoration-line-through" v-html="priceFormat(item.price)"></span>
                     -> {{ priceFormat(item.price * (100 - item.discount) / 100) }}
                   </div>
                    <div v-else>
                      <span class="text-muted" v-html="priceFormat(item.price)"></span>
                    </div>
                </div>
              </div>
              
              <!-- Product actions-->
              <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                <div class="text-center">
                  <button @click="get(item.ino)" class="btn btn-outline-dark mt-auto">Detail</button>
                </div>
              </div>
              <div v-if="member.id !== null" class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                <div class="text-center">
                  <button class="btn btn-outline-dark mt-auto">Add to cart</button>
                </div>
              </div>
            </div>
          </div>          
        </div>
      </div>
    </section>

    <nav aria-label="pagination" v-if="page.total > 0">
      <ul class="pagination justify-content-center mt-5">
        <li :class="page.prev ? 'page-item' : 'page-item disabled'">
          <a @click="pageClick(page.startPage - 1)" class="page-link" href="#" tabindex="-1" :aria-disabled="false">Prev</a>
        </li>
        <li v-for="(item, i) in numbers" :key="i" :class="item === page.criteriaDto.pageNum ? 'page-item active' : 'page-item'">
          <a @click="pageClick(item)" class="page-link" href="#">{{ item }}</a>
        </li>
        <li :class="page.next ? 'page-item' : 'page-item disabled'">
          <a @click="pageClick(page.endPage + 1)" class="page-link" href="#">Next</a>
        </li>
      </ul>
    </nav>
  </div>
</template>
<style scoped>
</style>