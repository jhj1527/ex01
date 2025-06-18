<script>
import { commonApi } from '@/service/common';

export default {
  name: 'UpdateView',
  data() {
    return {
      input: {},
      newReply: '',
      result: {},
    }
  },
  methods: {
    async updateReply() {
      try {
        this.result = await commonApi("/api/reply/update", "patch", this.reply);
        
        if (this.result.data === true) {
          alert("update");
          this.$emit("update");

        } else {
          alert("error");
        }
        
      } catch (e) {
        console.error(e);
      }
    },
  },
  props: {
    reply: {
      type: Object,
      default: () => ({}),
    },
    isModal: {
      type: Boolean,
      default: false,
    }
  },
  computed: {
    
  }, 
}
</script>

<template>
  <div class="reply-update-popup" v-if="isModal" >
    <div class="popup-content">
      <h2>댓글 수정</h2>
      <div class="form-group">
        <label>내용</label>
        <textarea v-model="reply.reply" class="form-control" rows="3" placeholder="댓글을 입력하세요"></textarea>
      </div>
      <div class="button-group">
        <button @click="updateReply" class="btn btn-primary">수정</button>
        <button @click="$emit('close')" class="btn btn-secondary">취소</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.reply-update-popup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.popup-content {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  width: 400px;
}

.form-group {
  margin-bottom: 15px;
}

.form-control {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
}

.button-group {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>