  import { useStore } from "@/stores/store";
  import axios from "axios";
  import { storeToRefs } from "pinia";
  import { ref } from "vue";

  export const commonApi = async(url, type, data) => {
    const res = ref([]);
    const store = useStore();
    const { member } = storeToRefs(store);

    try {
      const instance = axios.create({
        baseURL : "http://localhost:8081",
      });
      const header = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        // withCredentials : true,
      };

      if (url.includes("logout")) {
        instance.defaults.withCredentials = false;

      } else {
        instance.defaults.withCredentials = true;
      }

      switch (type.toUpperCase()) {
        case "GET":
          res.value = await instance.get(url);
          break;

        case "POST":
          if (url.includes("login") || url.includes("upload")) {
            res.value = await instance.post(url, data, header);

          } else {
            debugger;
            res.value = await instance.post(url, data);
          }
          break;

        case "PATCH":
          res.value = await instance.patch(url, data);
          break;

        case "DELETE":
          res.value = await instance.delete(url);
          break;
          
        default:
          break;
      }
      
      return res.value;

    } catch (e) {
      console.error(e.response);
      return e.response;
    }
  }