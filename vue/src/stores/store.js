import { ref, computed, reactive } from 'vue'
import { defineStore } from 'pinia'
import { commonApi } from '@/service/common';

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
    count : 0,
    checkArr : [],
  });

   const getCartCount = async (id) => {
    let param = {}
    param.id = id;
    const res = await commonApi("/api/cart/getCount", "get", param);
    cart.value.count = res.data;
  };

  return {  
    member,
    pager,
    cart,
    getCartCount,
  }
}, {persist:true});
