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
          const url = URL.createObjectURL(res.data);
          this.$refs.mainImage[i].src = url;

        } catch (e) {
          console.log(e);
        }
      },
      
    },
  };
</script>
<template>
  <div class="container">

    <div class="d-flex justify-content-between align-items-center mb-3">
      <div>
        <span>총 <span style="color: #e74c3c;" v-html="page.total"></span>건</span>
      </div>
      <div class="d-flex align-items-center justify-content-center flex-grow-1">
        <select class="form-select me-2" style="width: 120px;">
          <option value="">카테고리</option>
          <option value="electronics">전자제품</option>
          <option value="fashion">패션</option>
          <option value="books">도서</option>
        </select>
        <input type="text" class="form-control me-2" placeholder="상품명 검색" style="width: 300px;">
        <button class="btn btn-primary">검색</button>
      </div>

      <div class="d-flex align-items-center" style="font-size: 0.9rem;">
        <router-link
          to="?sort=new"
          class="fw-bold me-3 sort-link"
          active-class="active"
          exact
        >신상품순</router-link>
        <router-link
          to="?sort=popular"
          class="text-secondary me-3 sort-link"
          active-class="active"
        >인기상품순</router-link>
        <router-link
          to="?sort=low"
          class="text-secondary me-3 sort-link"
          active-class="active"
        >낮은가격순</router-link>
        <router-link
          to="?sort=high"
          class="text-secondary sort-link"
          active-class="active"
        >높은가격순</router-link>
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
                :src="getImage(item.attachList[0].filePath, item.attachList[0].fileName, i)"
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