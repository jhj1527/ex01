<script setup>
  import { commonApi } from '@/service/common';
  import { RouterLink, useRouter } from 'vue-router';
  import { onBeforeMount, onMounted, reactive, ref } from 'vue';
  import axios from "axios";
  import { useStore } from '@/stores/store';
  import { storeToRefs } from 'pinia';

  let numbers = [];
  const result = ref([]);
  // const emit = defineEmits(["popup"]);
  const page = ref({});
  const param = ref({
    pageNum : 1,
    amount : 10,
    type: "",
    keyword: "",
  });

  const store = useStore();
  const { member } = storeToRefs(store);

  onBeforeMount(() => {
    // pageList();
  });

  onMounted(() => {
    pageList();
  });

  const pageList = async () => {
    try {
      
      const url = "/api/board/list?" + new URLSearchParams(param.value).toString();

      result.value = await commonApi(url, "get");
      param.value = result.value.data.page.criteriaDto;
      page.value = result.value.data.page;
      result.value = result.value.data.list;
      
      // console.log(param.value);
      // console.log(page.value);
      // console.log(result.value);

      numbers = [];

      for (let i = page.value.startPage; i <= page.value.endPage; i++) {
        numbers.push(i);
      }

      // console.log(numbers);

    } catch (e) {
      console.error(e);
    }
  };
  
  const pageClick = (pageNum) => {
    param.value.pageNum = pageNum;
    pageList();
  }

  const search = () => {
    pageList();
  };
</script>

<template>
  <div class="container">
    <!-- <h2 class="">board</h2> -->
    <RouterLink to="/board/insert" class="btn btn-outline-primary mb-3">Insert</RouterLink>
    <div class="table-responsive">
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">title</th>
            <th scope="col">content</th>
            <th scope="col">id</th>
            <th scope="col">regDate</th>
            <th scope="col">updateDate</th>
            <th scope="col">viewCount</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in result" :key="item.bno">
            <td><RouterLink :to="`/board/get/${item.bno}`">{{ item.bno }}</RouterLink></td>
            <td>{{ item.title }}[{{ item.replyCount }}]</td>
            <td>{{ item.content }}</td>
            <td>{{ item.id }}</td>
            <td>{{ item.regDate }}</td>
            <td>{{ item.updateDate }}</td>
            <td>{{ item.viewCount }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="row mb-3 justify-content-center">
      <div class="col-md-2">
        <select class="form-select" v-model="param.type">
          <option value=""></option>
          <option value="title">Title</option>
          <option value="content">Content</option>
          <option value="id">Id</option>
          <option value="TC">Title + Content</option>
        </select>
      </div>
      <div class="col-md-4">
        <input type="text" v-model="param.keyword" @keyup.enter="search" class="form-control" placeholder="Search...">
      </div>
      <div class="col-md-2">
        <button class="btn btn-primary" @click="search">Search</button>
      </div>
    </div>

    <nav aria-label="pagination" v-if="page.total > 0">
      <ul class="pagination justify-content-center mt-5">
        <li :class="page.prev ? 'page-item' : 'page-item disabled'">
          <a @click="pageClick(page.startPage - 1)" class="page-link" href="#" tabindex="-1" :aria-disabled="false">Prev</a>
        </li>
        <li v-for="item in numbers" :key="item" :class="item === page.criteriaDto.pageNum ? 'page-item active' : 'page-item'">
          <a @click="pageClick(item)" class="page-link" href="#">{{ item }}</a>
        </li>
        <li :class="page.next ? 'page-item' : 'page-item disabled'">
          <a @click="pageClick(page.endPage + 1)" class="page-link" href="#">Next</a>
        </li>
      </ul>
    </nav>
  </div>

</template>

<style>
  /* .pagination a{
    margin-right: 3px;
  } */
</style>
