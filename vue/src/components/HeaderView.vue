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
        <li><RouterLink to="/member/info" class="nav-link px-2 link-dark">Info</RouterLink></li>
        <li><RouterLink to="" class="nav-link px-2 link-dark">About</RouterLink></li>
      </ul>

      <div class="col-md-3 text-end">
        <!-- <button type="button" class="btn btn-outline-primary me-2" @click="login">Login</button> -->
        <button type="button" v-if="member.id === null" class="btn btn-outline-primary me-2" @click="login">Login</button>
        <button type="button" v-else class="btn btn-outline-primary me-2" @click="logout">logout</button>
        <button type="button" class="btn btn-primary" @click="insert">Sign-up</button>
      </div>
    </header>
  </div>
</template>

<style>
  
</style>