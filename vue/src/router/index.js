import { useStore } from '@/stores/store';
import { storeToRefs } from 'pinia';
import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/board/list",
      name: "Boardlist",
      component: () => import('../views/board/ListView.vue'),
    },
    {
      path: "/board/insert",
      name: "boardInsert",
      component: () => import('../views/board/InsertView.vue'),
    },
    {
      path: "/board/get/:bno",
      name: "get",
      component: () => import('../views/board/GetView.vue'),
      props : true,
    },
    {
      path: "/member/insert",
      name: "memberInsert",
      component: () => import('../views/member/InsertView.vue'),
    },
    {
      path: "/member/login",
      name: "login",
      component: () => import('../views/member/LoginView.vue'),
    },
    {
      path: "/member/get",
      name: "info",
      component: () => import('../views/member/GetView.vue'),
    },
    {
      path: "/reply/list",
      name: "replyList",
      component: () => import('../views/reply/ReplyView.vue'),
    },
    {
      path: "/reply/update",
      name: "replyUpdate",
      component: () => import('../views/reply/UpdateView.vue'),
    },
    {
      path: "/item/insert",
      name: "itemInsert",
      component: () => import('../views/item/InsertView.vue'),
    },
  ],
});

router.beforeEach((to, from, next) => {
  const store = useStore();
  const { member } = storeToRefs(store);
  const urlArr = [
    '/member/login', 
    '/member/insert', 
    '/board/list', 
    '/board/get', 
    '/reply/list', 
  ];

  let result = urlArr.filter(i => to.path.includes(i)); 

  if (result.length !== 0 || to.path === "/") {
    next();

  } else if (result.length === 0 && member.value.id === null) {
    next("/member/login");

  } else if (member.value.id !== null) {
    next();
  } 
});

export default router
