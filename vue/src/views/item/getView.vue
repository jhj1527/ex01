<script>
  import { commonApi } from '@/service/common';
  import { useStore } from '@/stores/store';
  import { mapState } from 'pinia';

  export default {
    name: 'GetView',
    data() {
      return {
        mainImage: '',
        images: [],
        quantity: 1,
        result : {},
      };
    },
    mounted() {
      this.get(this.ino);
      this.test();
    },
    computed: {
      ...mapState(useStore, ["member"]),
    },
    methods: {
      async get(ino) {
        // try {
        //   const res = await commonApi(`/api/item/${ino}`, 'GET');
        //   console.log(res);
        //   this.result = res.data;
          
        // } catch (e) {
        //   console.log(e);
        // }
      },
      async test() {
        try {
          const params = {
            filePath: "b",
            fileName: "e008a331-4f65-4823-9c74-25fdd807b233_mountain-8531778_640.jpg" 
          }
          const res = await commonApi("/api/file/getFile", "GET", params);
          console.log(res.data);

          const url = URL.createObjectURL(res.data);

          this.$refs.mainImage.src = url;

          // console.log(url);
        } catch (e) {
          console.log(e);
        }
      },
    },
    props: {
      ino: [Number, String]
    },
  }
</script>

<template>
  <div class="container my-5">
    <div class="row">
      <!-- Product Image -->
      <div class="col-md-6 d-flex flex-column align-items-center">
        <!-- 메인 이미지 -->
        <img
          :src="test"
          alt="상품 이미지"
          ref="mainImage"
          class="img-fluid rounded shadow-sm mb-3"
          style="max-height: 350px; object-fit: contain;"
        />
        <!-- 썸네일 이미지 목록 -->
        <div class="d-flex gap-2">
          <!-- <img
        v-for="(img, idx) in images"
        :key="idx"
        :src="`http://localhost:8081/api/file/getFile?filePath=b&fileName=e008a331-4f65-4823-9c74-25fdd807b233_mountain-8531778_640.jpg`"
        alt="썸네일"
        class="img-thumbnail"
        :class="{ 'border-primary': mainImage === img }"
        style="width: 60px; height: 60px; object-fit: cover; cursor: pointer;"
        @click="mainImage = img"
          /> -->
          <!-- <img :src="`http://localhost:8081/api/file/getFile?filePath=b&fileName=e008a331-4f65-4823-9c74-25fdd807b233_mountain-8531778_640.jpg`" 
          alt=""
          class="img-thumbnail"

          style="width: 100px; height: 100px; object-fit: cover; cursor: pointer;"> -->
        </div>
      </div>
      <!-- Product Details -->
      <div class="col-md-6">
        <h2 class="mb-3">상품명 예시</h2>
        <p class="text-muted">카테고리: 전자제품</p>
        <h4 class="text-primary mb-3">₩99,000</h4>
        <!-- <p>
          이 상품은 최신 기술이 적용된 고품질 제품입니다. 다양한 기능과 세련된 디자인으로 일상에 편리함을 더해줍니다.
        </p>
        <ul class="list-group mb-3">
          <li class="list-group-item">특징 1: 고성능</li>
          <li class="list-group-item">특징 2: 합리적인 가격</li>
          <li class="list-group-item">특징 3: 1년 무상 A/S</li>
        </ul> -->
        <div class="mb-3 d-flex align-items-center">
          <label for="quantity" class="form-label me-3 mb-0">수량</label>
          <button
            type="button"
            class="btn btn-outline-secondary"
            @click="quantity > 1 && (quantity--)"
            aria-label="수량 감소"
          >-</button>
          <span class="mx-3">{{ quantity }}</span>
          <button
            type="button"
            class="btn btn-outline-secondary"
            @click="quantity++"
            aria-label="수량 증가"
          >+</button>
        </div>
        <button class="btn btn-primary">
          장바구니 담기
        </button>
      </div>
    </div>
    <!-- Product Description Tabs -->
    <div class="row mt-5">
      <div class="col-12">
        <ul class="nav nav-tabs" id="descTab" role="tablist">
          <li class="nav-item" role="presentation">
            <button
              class="nav-link active"
              id="desc-tab"
              data-bs-toggle="tab"
              data-bs-target="#desc"
              type="button"
              role="tab"
              aria-controls="desc"
              aria-selected="true"
            >
              상세 설명
            </button>
          </li>
          <li class="nav-item" role="presentation">
            <button
              class="nav-link"
              id="review-tab"
              data-bs-toggle="tab"
              data-bs-target="#review"
              type="button"
              role="tab"
              aria-controls="review"
              aria-selected="false"
            >
              리뷰
            </button>
          </li>
        </ul>
        <div class="tab-content p-3 border border-top-0" id="descTabContent">
          <div
            class="tab-pane fade show active"
            id="desc"
            role="tabpanel"
            aria-labelledby="desc-tab"
          >
            <p>
              이곳에 상품의 상세 설명이 들어갑니다. 크기, 무게, 사용법 등 다양한 정보를 제공할 수 있습니다.
            </p>
          </div>
          <div
            class="tab-pane fade"
            id="review"
            role="tabpanel"
            aria-labelledby="review-tab"
          >
            <p>아직 등록된 리뷰가 없습니다.</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 추가적인 스타일이 필요하면 여기에 작성 */
</style>