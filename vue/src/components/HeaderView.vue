<script setup>
  import { commonApi } from '@/service/common';
  import { useStore } from '@/stores/store';
  import { storeToRefs } from 'pinia';
  import { ref } from 'vue';
  import { RouterLink, useRouter } from 'vue-router';
  const router = useRouter();
  const result = ref("");
  const store = useStore();
  const { member } = storeToRefs(store);

  const insert = () => {
    router.push("/member/insert");
  }

  const login = () => {
    router.push("/member/login");
  }

  const logout = async () => {
    result.value = await commonApi("/api/member/logout", "POST");
    console.log(result.value);

    localStorage.clear();
    
    member.value.id = null;
    member.value.sessionId = null;
    member.value.role = null;

    window.alert("logout");
    router.push("/");
  }
</script>

<template>
  <div class="container">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
      <a href="/" class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none">
        <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"></use></svg>
      </a>

      <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
        <li><RouterLink to="/" class="nav-link px-2 link-secondary">Home</RouterLink></li>
        <li><RouterLink to="/board/list" class="nav-link px-2 link-dark">Board</RouterLink></li>
        <li><RouterLink to="/member/get" class="nav-link px-2 link-dark">Info</RouterLink></li>
        <li><RouterLink to="/order/insert" class="nav-link px-2 link-dark" >insert</RouterLink></li>
        <li><RouterLink to="/item/list" class="nav-link px-2 link-dark">itemList</RouterLink></li>
      </ul>
      
      <ul class="nav col-md-3 text-end">
        <li v-if="member.id === null" class="ms-3">
          <RouterLink to="/member/login" style="color: black;"><i class="bi bi-person" style="font-size: 1.5rem;"></i></RouterLink>
        </li>
        <li v-else class="ms-3">
          <i class="bi bi-person-check" @click="logout" style="font-size: 1.5rem; cursor: pointer;">{{ member.id }}</i>
        </li>
        <li v-if="member.id === null" class="ms-3">
          <RouterLink to="/member/insert" style="color: black;"><i class="bi bi-person-add" style="font-size: 1.5rem;"></i></RouterLink>
        </li>
        <li class="ms-3">
          <RouterLink to="/cart/list" :id="member.id" style="color: black;"><i class="bi bi-cart" style="font-size: 1.5rem;"></i></RouterLink>
        </li>
      </ul>
    </header>
  </div>
</template>

<style>
  
</style>