import { ref, computed, reactive } from 'vue'
import { defineStore } from 'pinia'

export const useStore = defineStore('store', () => {
  const member = ref({
    id : null,
    sessionId : null,
    role : null,
  });

  const pager = ref({
    pageNum : 1,
    amount : 10,
  });

  const cart = ref({
    checkedArr : [],
  });

  const checkArr = ref([]);

  return {  
    member,
    pager,
    cart,
    checkArr,
  }
}, {persist:true});
