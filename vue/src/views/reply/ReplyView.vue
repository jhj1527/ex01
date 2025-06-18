<script>
import { commonApi } from '@/service/common';
import { useStore } from '@/stores/store'
import { mapState } from 'pinia'
import UpdateView from './UpdateView.vue';

export default {
  name: 'ReplyView',
  data() {
    return {
      numbers: [],
      newReply: "",
      page : [],
      result: [],
      isModal:false,
      reply: {},
    }
  },
  props: {
    bno: [Number, String]
  },
  computed: {
    ...mapState(useStore, ['member']),
  },
  mounted() {
    this.replyList();
  },
  methods: {
    async replyList() {
      try {
        if (Object.keys(this.page).length === 0) {
          this.result = await commonApi(`/api/reply/list/${this.bno}/1`, 'get');
          
        } else {
          this.result = await commonApi(`/api/reply/list/${this.bno}/${this.page.criteriaDto.pageNum}`, 'get');
        }

        this.page = this.result.data.page;
        this.result = this.result.data.list;

        this.numbers = [];
        
        for (let i = this.page.startPage; i <= this.page.endPage; i++) {
          this.numbers.push(i);
        }
        
      } catch (e) {
        console.error(e);
      }
    },
    async addReply() {
      try {
        if (this.newReply.trim()) {
          const replyData = {
            bno: this.bno,
            reply: this.newReply,
            id: this.member.id
          };
          // console.log(replyData);
          
          this.result = await commonApi('/api/reply/insert', 'post', replyData);

          if (this.result.data === 1) {
            alert("insert");
            this.newReply = ""; 
            this.replyList();
          }
        }

      } catch (e) {
        console.error(e);
      }
    },
    async deleteReply(rno) {
      try {
        const res = await commonApi("/api/reply/delete/" + rno, "DELETE");
  
        if (res.data === true) {
          alert("delete");
          this.result = this.result.filter(reply => reply.rno !== rno);
          this.replyCnt -= 1;
        }
        
      } catch (e) {
        console.error(e);
      }
    },
    updateReply(reply) {
      this.isModal = true;
      this.reply = { ...reply }; // 자식 컴포넌트에서 원본 데이터 변경 방지하기 위해 얕은 복사
    },
    close() {
      this.isModal = !this.isModal;
    },
    update() {
      this.isModal = !this.isModal;
      this.replyList();
    },
    pageClick(pageNum) {
      this.page.criteriaDto.pageNum = pageNum;
      this.replyList();
    },
  },
  components: {
    UpdateView
  },  
}
</script>

<template>
  <div class="mt-5">
    <!-- 댓글 작성 폼 -->
    <div class="card mb-4" v-if="member.id !== null">
      <div class="card-body">
        <h5 class="card-title">댓글 작성</h5>
        <div class="mb-3">
          <textarea class="form-control" v-model="newReply" rows="3" placeholder="댓글을 입력하세요"></textarea>
        </div>
        <div class="d-flex justify-content-end">
          <button @click="addReply" class="btn btn-sm btn-outline-primary">댓글 등록</button>
        </div>
      </div>
    </div>

    <!-- 댓글 목록 -->
    <div class="card mb-2" v-for="(reply, i) in result" :key="reply.rno">
      <div class="card-body">
        <div class="d-flex justify-content-between">
          <h6 class="card-subtitle mb-2 text-muted">{{ reply.id }}</h6>
          <small class="text-muted">{{ reply.regDate }}</small>
        </div>
        <p class="card-text">{{ reply.reply }}</p>
        <div v-if="reply.id === member.id" class="d-flex justify-content-end">
          <button @click="updateReply(reply)" class="btn btn-sm btn-outline-primary me-2">수정</button>
          <button @click="deleteReply(reply.rno)" class="btn btn-sm btn-outline-danger">삭제</button>
        </div>
      </div>
    </div>
    
    <nav aria-label="pagination" v-if="page.total > 0">
      <ul class="pagination justify-content-center mt-5">
        <li :class="page.prev ? 'page-item' : 'page-item disabled'">
          <a @click="pageClick(page.startPage - 1)" class="page-link" href="#" tabindex="-1" :aria-disabled="false">Prev</a>
        </li>
        <li v-for="item in numbers" :class="item === page.criteriaDto.pageNum ? 'page-item active' : 'page-item'">
          <a @click="pageClick(item)" class="page-link" href="#">{{ item }}</a>
        </li>
        <li :class="page.next ? 'page-item' : 'page-item disabled'">
          <a @click="pageClick(page.endPage + 1)" class="page-link" href="#">Next</a>
        </li>
      </ul>
    </nav>
  </div>

  <UpdateView :isModal="isModal" :reply="reply" @close="close()" @update="update()"/>
</template>

<style scoped>
.card {
  border-radius: 8px;
}
.card-body {
  padding: 1.25rem;
}
.card-title {
  font-weight: bold;
}
</style>