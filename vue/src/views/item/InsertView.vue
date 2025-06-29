<script>
  import { useStore } from '@/stores/store';
  import { mapState, storeToRefs } from 'pinia';
  import { computed, reactive, ref, useTemplateRef } from 'vue'
  import { useRouter } from 'vue-router';
  import { commonApi } from '@/service/common';

  export default {
    name: "InsertView",
    data() {
      return {
        input: {
          name: "",
          price: "",
          discount: "",
          category: "",
          content: "",
          attachList: [],
        },
        result: [],
      };
    },
    computed: {
      ...mapState(useStore, ["member"]),
    },
    methods: {
      numberFilter(e, col) {
        // 숫자만 입력 가능하도록 필터링
        if (col.startsWith("price")) {
          this.input.price = this.input.price.replace(/[^0-9]/g, '');
          this.input.price = Number(this.input.price);

        } else if (col.startsWith("discount")) {
          this.input.discount = this.input.discount.replace(/[^0-9]/g, '');
          this.input.discount = Number(this.input.discount);
        }
      },
      async onFileChange(e) {
        this.result = [];
        const formData = new FormData();

        if (e.target.files.length === 0) {
          return;
        }
        
        for (let i = 0; i < e.target.files.length; i++) {
          formData.append('file', e.target.files[i]);
        }

        formData.append("folderPath" , this.input.category);

        // for (let value of formData.values()) {
        //   console.log(value);
        // }

        // for (let key of formData.keys()) {
        //   console.log(formData.get(key));
        // }

        try {
          const res = await commonApi("/api/file/upload", "POST", formData);
          this.result = res.data;

          // console.log(this.result);
        } catch (e) {
          console.log("error : ", e);
        }
      },
      async deleteFile(i) {
        try {
          const res = await commonApi("/api/file/delete", "POST", this.result[i]);
          // 해당 인덱스의 파일 객체 삭제
          this.result = this.result.filter(file => file.attachId !== this.result[i].attachId);

        } catch (e) {
          console.log("error : ", e);
        }
      },
      async submitForm() {
        try {
          debugger
          if (this.result.length !== 0) {
            this.input.attachList = this.result;  
          }

          const res = await commonApi("/api/item/insert", "POST", this.input);

          if (res.status === 201) {
            alert("insert");

            this.input.name = "";
            this.input.price = "";
            this.input.discount = "";
            this.input.category = "";
            this.input.content = "";
            this.input.attachList = [];
            this.result = [];
            this.$refs.inputFile.value = null;

          } else {
            alert("error");
          }
          
        } catch (e) {
          console.log("error : ", e);
        }
      },
    },
    // emits: ["popup"],
    // props: {
    //   bno: [Number, String]
    // },
  };
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
          <option value="fruit">과일</option>
          <option value="vegetable">채소</option>
        </select>
      </div>
      <div class="mb-3">
        <label for="description" class="form-label">상품 설명</label>
        <textarea class="form-control" id="description" v-model="input.content" rows="3"></textarea>
      </div>
      <div class="mb-3">
        <label for="image" class="form-label">상품 이미지</label>
        <input type="file" @change="onFileChange($event)" ref="inputFile" class="form-control" id="image" multiple>
      </div>
      <div class="mb-3" v-if="result.length > 0">
        <label class="form-label">업로드된 이미지 미리보기</label>
        <div class="d-flex flex-wrap gap-2">
          
          <div v-for="(image, i) in result" :key="image.attchId">
            <img :src="`http://localhost:8081/api/file/getFile?filePath=${image.filePath}&fileName=${image.fileName}`" v-if="image.fileType" alt="업로드 이미지" class="img-thumbnail">
            <p @click="deleteFile(i)" v-html="image.fileName"></p>
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