<script setup>
  import { commonApi } from '@/service/common';
  import { useStore } from '@/stores/store';
  import axios from 'axios';
  import { storeToRefs } from 'pinia';
  import { reactive, ref, defineEmits } from 'vue';
  import { RouterLink, useRouter } from 'vue-router';

  const router = useRouter();
  const store = useStore();
  const { member } = storeToRefs(store);
  const input = reactive({
    id : "",
    password : "",
  });
  const modalCheck = ref(false);
  const result = ref("");
  const emit = defineEmits(["popup"]);
  
  const login = async () => {
    try {
      
      result.value = await commonApi("/api/member/login", "POST", input);
  
      if (result.value.status === 200) {
        member.value.id = result.value.data.id;
        member.value.sessionId = result.value.data.sessionId;
        member.value.role = result.value.data.role[0];
      
        localStorage.setItem("id" , member.value.id);
        localStorage.setItem("sessionId" , member.value.sessionId);
        localStorage.setItem("role" , member.value.role);
  
        console.log(member.value.id);
        console.log(member.value.sessionId);
        console.log(member.value.role);
        
        window.alert("login");
        router.push("/");
        
      } else {
        alert(result.value.data.message);
      }

    } catch (e) {
      console.log(e);
    }
  };

</script>

<template>
  <body class="text-center">
    <main class="form-signin">
      <form @submit.prevent="login" id="loginForm">
        <img class="mb-4" src="../../assets/logo.svg" alt="" width="72" height="57">
        <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

        <div class="form">
          <!-- <label for="id">id</label> -->
          <input type="text" v-model="input.id" class="form-control" id="id" placeholder="id">
        </div>
        <div class="form">
          <!-- <label for="password">Password</label> -->
          <input type="text" v-model="input.password" class="form-control" id="password" placeholder="Password">
        </div>

        <div class="checkbox mb-3">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
        <div class="links">
          <router-link to="/member/insert">register</router-link> |
          <router-link to="">forgot password</router-link>
        </div>
        <!-- <p class="mt-5 mb-3 text-muted">&copy; 2017–2021</p> -->
      </form>
    </main>
  </body>
  
  <!-- <div class="login-container">
    <div class="login-box">
      <h2>login</h2>
      <form @submit.prevent="login" id="loginForm">
        <div class="form-group">
          <label for="username">id</label>
          <input type="text" v-model="input.id" id="id" required placeholder="아이디를 입력하세요">
        </div>
        <div class="form-group">
          <label for="password">password</label>
          <input type="text" v-model="input.password" id="password" required placeholder="비밀번호를 입력하세요">
        </div>
        <button type="submit" class="login-btn">로그인</button>
      </form>
      <div class="links">
        <router-link to="/member/insert">register</router-link> |
        <router-link to="">forgot password</router-link>
      </div>
    </div>
  </div> -->

</template>

<style scoped>
body {
  height: 100%;
}

body {
  display: flex;
  align-items: center;
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #f5f5f5;
}

.form-signin {
  width: 100%;
  max-width: 330px;
  padding: 15px;
  margin: auto;
}

.form-signin .checkbox {
  font-weight: 400;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.login-box {
  background: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

h2 {
  text-align: center;
  margin-bottom: 2rem;
  color: #333;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  color: #666;
}

input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 1rem;
}

.login-btn {
  width: 100%;
  padding: 0.75rem;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.login-btn:hover {
  background-color: #45a049;
}

.links {
  text-align: center;
  margin-top: 1rem;
}

.links a {
  color: #666;
  text-decoration: none;
  margin: 0 0.5rem;
}

.links a:hover {
  color: #4c70af;
}
</style>
