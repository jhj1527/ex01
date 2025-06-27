// import './assets/main.css'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-icons/font/bootstrap-icons.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import App from './App.vue'
import router from './router'
import axios from 'axios'

const app = createApp(App);
const pinia = createPinia(App);
pinia.use(piniaPluginPersistedstate);

app.use(pinia);
app.use(router);
app.provide("$axios", axios);

app.mount('#app');
