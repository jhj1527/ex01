<script setup>
  import { RouterLink, RouterView, useRouter, useRoute } from 'vue-router'
  import DefaultLayout from './components/DefaultLayout.vue';
  import { onBeforeMount, onBeforeUnmount, ref, watch } from 'vue';
  import { storeToRefs } from 'pinia';
  import { useStore } from './stores/store';
  import { Modal } from 'bootstrap';
  
  const router = useRouter();
  const store = useStore();
  const { member } = storeToRefs(store);
  const init = () => {
    let vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  };
  onBeforeMount(() => {
    // init();
    // window.addEventListener("resize", init);
  });
  onBeforeUnmount(() => {
    // window.removeEventListener("resize", init);
  });
  
  watch(member, (curVal, prevVal) => {
     console.log(curVal, prevVal); 
  });

  const popup = (result, state) => {
    console.log(result);
    
    if (result.status === 200) {
      window.alert(state);
      
      // if (result.request.responseURL.includes("board")) {
      //   router.push("/board/list");
      // }

      if (result.request.responseURL.includes("/member/insert")) {
        router.push("/member/login");
      }
    }
  };


</script>

<template>
  <DefaultLayout>
    <RouterView @popup="popup"/>
  </DefaultLayout>


<!-- 모달 -->
<!-- <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p v-html="modalMsg"></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div> -->
</template>


<style>

</style>
