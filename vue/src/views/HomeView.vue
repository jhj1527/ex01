<script setup>
  import { commonApi } from '@/service/common';
  import { RouterLink, useRouter } from 'vue-router';
  import { onBeforeMount, ref } from 'vue';
  import axios from "axios";

  const res = ref([]);
  const router = useRouter();
  
</script>

<template>
  <div class="container">
    <h1>home</h1>
  </div>
</template>

<style>

</style>
