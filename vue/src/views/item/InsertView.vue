<script setup>
  import { useStore } from '@/stores/store';
  import { storeToRefs } from 'pinia';
  import { computed, reactive, ref, useTemplateRef } from 'vue'
  import { useRouter } from 'vue-router';
  import { commonApi } from '@/service/common';

  const router = useRouter();
  const store = useStore();
  const { member } = storeToRefs(store);

  const input = reactive({
    name: "",
    price: "",
    discount: "",
    category: "",
    content: "",
    // attachList: [],
  });

  const result = ref([]);

  const numberFilter = (e, col) => {
    // 숫자만 입력 가능하도록 필터링
    if (col.startsWith("price")) {
      input.price = input.value.replace(/[^0-9]/g, '');
      input.price = Number(input.price);

    } else if (col.startsWith("discount")) {
      input.discount = input.discount.replace(/[^0-9]/g, '');
      input.discount = Number(input.discount);
    }
  }

  const onFileChange = async(e) => {
    result.value = "";

    if (e.target.files.length === 0) {
      return;
    }
    // console.log(e.target.files);
    const formData = new FormData();

    for (let i = 0; i < e.target.files.length; i++) {
      formData.append('file', e.target.files[i]);
    }

    formData.append("folderPath" , input.category);

    // for (let value of formData.values()) {
    //     console.log(value);
    // }
    

    // for (let key of formData.keys()) {
    //   console.log(formData.get(key));
    // }

    try {
      result.value = await commonApi("/api/file/upload", "POST", formData);

      console.log(result.value.data);

    } catch (e) {
      console.log("error : ", e);
    }
  }

  const deleteFile = async(i) => {
    try {
      const res = await commonApi("/api/file/delete", "POST", result.value.data[i]);

      // 해당 인덱스의 파일 객체 삭제
      result.value.data = result.value.data.filter(file => 
        file.attchId !== result.value.data[i].attchId
      );

      console.log(result.value.data);

      // inputFile.value.value = '';
  
    } catch (e) {
      console.log("error : ", e);
    }
  }

  const submitForm = async() => {
    try {
      input.attachList = result.value.data;

      console.log(input);

      const res = await commonApi("/api/item/insert", "POST", input);

      input = {
        name: "",
        price: "",
        discount: "",
        category: "",
        content: "",
        attachList: [],
      };

      result.value.data = "";
  
    } catch (e) {
      console.log("error : ", e);
    }
  }
</script>

<template>
  <div class="container mt-5">
    <h2 class="mb-4">상품 등록</h2>
    <form @submit.prevent="submitForm">
      <div class="mb-3">
        <label for="name" class="form-label">상품명</label>
        <input type="text" v-model="input.name" class="form-control" id="name" required>
      </div>
      <div class="mb-3">
        <label for="price" class="form-label">가격</label>
        <input type="text" v-model="input.price" @input="numberFilter($event, 'price')" class="form-control" id="price" required>
      </div>
      <div class="mb-3">
        <label for="discount" class="form-label">할인</label>
        <input type="text" v-model="input.discount" @input="numberFilter($event, 'discount')" class="form-control" id="discount" required>
      </div>
      <div class="mb-3">
        <label for="category" class="form-label">카테고리</label>
        <select v-model="input.category" class="form-select" id="category">
          <option value="" disabled>카테고리 선택</option>
          <option value="a">의류</option>
          <option value="b">전자제품</option>
          <option value="c">식품</option>
          <option value="d">기타</option>
        </select>
      </div>
      <div class="mb-3">
        <label for="description" class="form-label">상품 설명</label>
        <textarea class="form-control" id="description" v-model="input.content" rows="3"></textarea>
      </div>
      <div class="mb-3">
        <label for="image" class="form-label">상품 이미지</label>
        <input type="file" @change="onFileChange($event)" ref="inputFile" class="form-control"id="image" multiple>
      </div>
      <div class="mb-3" v-if="result.data">
        <label class="form-label">업로드된 이미지 미리보기</label>
        <div class="d-flex flex-wrap gap-2">
          
          <div v-for="(image, i) in result.data" :key="image.attchId">
            <img :src="`http://localhost:8081/api/file/getFile?filePath=${image.filePath}&fileName=${image.fileName}`" v-if="image.fileType" alt="업로드 이미지" class="img-thumbnail">
            <p @click="deleteFile(i)">{{ image.fileName }}</p>
          </div>
        </div>
      </div>
      <button type="submit" class="btn btn-primary">등록하기</button>
    </form>
  </div>
</template>

<style scoped>
  .container {
    max-width: 500px;
  }
  img {
    width: 100px; 
    height: 100px; 
    object-fit: cover; 
    border: 1px solid #ddd; 
    border-radius: 4px;
  }
</style>