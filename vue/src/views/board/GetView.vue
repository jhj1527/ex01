<script>
  import { commonApi } from '@/service/common';
  import { useStore } from '@/stores/store';
  import { mapState, mapStores } from 'pinia';
  import ReplyView from '../reply/ReplyView.vue';

  export default {
    data() {
      return {
        input : {},
        result : {},
        // store : useStore()
      };
    },
    emits: ["popup"],
    props: {
      bno: [Number, String]
    },
    computed: {
      ...mapState(useStore, ["member"]),
    },
    methods: {
      async get() {
        try {
          // this.result = await commonApi("/api/board/" + this.$route.params.bno, "GET");

          this.result = await commonApi("/api/board/" + this.bno, "GET");
          this.input = this.result.data
          // console.log(this.input);

        } catch (e) {
          console.error(e);
        }
      },
      async update() {
        try {
          this.result = await commonApi("/api/board/" + this.input.bno, "PATCH", this.input);

          if (this.result.data === 1) {
            // this.$emit("popup", this.result, "update");
            window.alert("update");
            this.get();
          }

        } catch (e) {
          console.error(e);
        }
      },
      async remove() {
        try {
          this.result = await commonApi("/api/board/" + this.input.bno, "DELETE");

          if (this.result.data === 1) {
            // this.$emit("popup", this.result, "delete");
            window.alert("delete");
            this.$router.push("/board/list");
          }

        } catch (e) {
          console.error(e);
        }
      },
      list() {
        this.$router.push('/board/list');
      },
    },
    beforeMount() {
      this.get();
    },
    mounted() {
      
    },
    components: {
      ReplyView,
    },
    setup() {
      // const store = useStore();
      // const { member } = storeToRefs(store);
      // return { member };
    }
  };
</script>

<template>
  <div class="container mt-5">
    <div class="card">
      <div class="card-header bg-primary text-white">
        <h3>게시글 상세</h3>
      </div>
      <div class="card-body">
        <div v-if="input.id === member.id" class="card-title mb-3">
          <input type="text" v-model="input.title" class="form-control">
        </div>
        <h4 v-else v-text="input.title" class="card-title mb-3"></h4>
        <div class="mb-2 text-muted">
          작성자: {{ input.id }} | 작성일: {{ input.regDate }}
        </div>
        <hr>
        <div v-if="input.id === member.id" class="mb-3">
          <textarea v-model="input.content" class="form-control" rows="3"></textarea>
        </div>
        <p v-else class="card-text" v-text="input.content" style="min-height: 60px;"></p>
      </div>
      <div class="card-footer text-end">
        <button type="button" @click="list" class="btn btn-sm btn-outline-secondary me-2">List</button>
        <button type="button" v-if="input.id === member.id" 
          @click="update" class="btn btn-sm btn-outline-primary me-2">Update</button>
        <button type="button" v-if="input.id === member.id"
          @click="remove" class="btn btn-sm btn-outline-danger">Delete</button>
      </div>
    </div>

    <ReplyView :bno="bno"/> 
  </div>
</template>

<style>

</style>

