<script>
  import { commonApi } from '@/service/common';
  import { useStore } from '@/stores/store';
  import { mapState } from 'pinia';

  export default {
    name : "getView",
    data() {
      return {
        result: [],
        checkedArr: [],
        input: {
          id: "",
          cno: "",
          ino: "",
          price: 0,
        },
        param: {
          id : ""
        },
        totalPrice: 0,
        charge : 0,
      };
    },
    // props: {
    //   id: String,
    // },
    mounted() {
      this.get(this.member.id);
    },
    beforeRouteLeave(to, from, next) {
      // 뒤로가기 시 실행될 로직
      this.allUpdate();

      next();
    },

    computed: {
      ...mapState(useStore, ["member"]),
      priceFormat() {
        return price => price ? price.toLocaleString() : '';
      },
      increase() {
        return idx => this.result[idx].amount < 10 ? this.result[idx].amount++ : this.result[idx].amount = 10;
      },
      decrease() {
        return idx => this.result[idx].amount > 1 ? this.result[idx].amount-- : this.result[idx].amount = 1;
      },
      allCheck : {
        get() {
          return this.result.length === this.checkedArr.length;
        },
        set(value) {
          value ? this.checkedArr = this.result.map(item => item.cno) : this.checkedArr = [];
        },
      },
      total() {
        const res = [...this.result];

        return res
        .filter(item => this.checkedArr.includes(item.cno))
        .reduce((sum, item) => sum + item.price * item.amount, 0);
      },
      fee() {
        return this.total >= 30000 ? 0 : 3000;
      },
    },
    methods : {
      async get(id) {
        this.param.id = id;
        const res = await commonApi("/api/cart/list", 'GET', this.param);

        this.result = res.data;
        this.result.forEach((data, i) => {
          // data.check = true;
        });

        this.checkedArr = this.result.map(item => item.cno);
      },
      removeItem(idx) {
        
      },
      amountChange(e, idx) {
        if (e.target.value > 10) {
          e.target.value = 10;

        } else if (e.target.value < 1) {
          e.target.value = 1;
        }
        this.result[idx].amount = e.target.value;
      },
      async allUpdate() {
        const res = await commonApi("/api/cart/updateList", "patch", this.result);

        console.log(res);
      },
      checkOut() {
        let res = [...this.result];
        res = res.filter(item => this.checkedArr.some(cno => item.cno === cno));
        console.log(res);

        this.totalPrice = this.total + this.fee;
      }
    },
  }
  // const coupon = ref('')

  // const subtotal = computed(() =>
  //   cartItems.value.reduce((sum, item) => sum + item.price * item.qty, 0)
  // )
  // const shipping = 3.0
  // const total = computed(() => subtotal.value + shipping)

  // function removeItem(idx) {
  //   cartItems.value.splice(idx, 1)
  // }
</script>

<template>
  <div class="container my-5">
    {{ checkedArr }}
    <div class="row">
      <!-- Cart Table -->
      <div class="col-lg-8">
        <table class="table align-middle">
          <thead>
            <tr>
              <th scope="col">
                <!-- <input type="checkbox" :checked="result.length && result.every(item => item.checked)" @change="toggleAll($event)"> -->
                 <input type="checkbox" v-model="allCheck">
              </th>
              <th scope="col">Products</th>
              <th scope="col">Name</th>
              <th scope="col">Price</th>
              <th scope="col">Quantity</th>
              <th scope="col">subTotal</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, idx) in result" :key="item.cno">
              <td>
                <input type="checkbox" v-model="checkedArr" :id="item.name" :value="item.cno" :index="item.cno">
                <label :for="item.name"></label>
              </td>
              <td>
                <img 
                  v-if="item.attachList.length > 0"  
                  :src="`http://localhost:8081/api/file/getFile?filePath=${item.attachList[0].filePath}&fileName=${item.attachList[0].fileName}`" 
                  alt="" class="rounded" style="width:70px; height:70px; object-fit:cover;" >
                </img>
                <img v-else 
                  src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" 
                  alt="" class="rounded" style="width:70px; height:70px; object-fit:cover;">
                </img>
              </td>
              <td>{{ item.name }}</td>
              <td>{{ item.price }}</td>
              <td>
                <div class="d-flex align-items-center">
                  <button @click="decrease(idx)" class="btn btn-outline-secondary btn-sm rounded-circle me-2">
                    <i class="bi bi-dash"></i>
                  </button>
                  <!-- <input type="text" v-model="item.amount" @change="amountChange($event, idx)" style="width:30px; text-align: center;" > -->
                  <span>{{ item.amount }}</span>
                  <button @click="increase(idx)" class="btn btn-outline-secondary btn-sm rounded-circle ms-2">
                    <i class="bi bi-plus"></i>
                  </button>
                </div>
              </td>
              <td>{{ (item.price * item.amount) }}</td>
              <td>
                <button class="btn btn-outline-danger btn-sm rounded-circle"
                  @click="removeItem(idx)">
                  <i class="bi bi-x"></i>
                </button>
              </td>
            </tr>
          </tbody>
        </table>
        <!-- Coupon -->
        <!-- <div class="row mt-4">
          <div class="col-md-4">
            <input type="text" class="form-control" placeholder="Coupon Code" v-model="coupon">
          </div>
          <div class="col-md-4">
            <button class="btn btn-primary px-4" @click="applyCoupon">
              Apply Coupon
            </button>
          </div>
        </div> -->
      </div>
      <!-- Cart Summary -->
      <div class="col-lg-4">
        <div class="bg-light rounded p-4 ms-lg-4 mt-4 mt-lg-0">
          <h3 class="fw-bold mb-4"><span class="fw-bolder">Cart</span> Total</h3>
          <div class="d-flex justify-content-between mb-2">
            <span>total:</span>
            <span>{{ total }}</span>
          </div>
          <div class="mb-2">
            <div class="d-flex justify-content-between">
              <span>fee</span>
              <span>Flat rate: {{ fee }}</span>
            </div>
          </div>
          <hr>
          <div class="d-flex justify-content-between mb-4">
            <span class="fw-bold">Total</span>
            <span class="fw-bold">{{total + fee}}</span>
          </div>
          <button @click="checkOut" class="btn btn-outline-warning w-100 py-2 fw-bold">
            PROCEED CHECKOUT
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .table th, .table td {
    vertical-align: middle;
  }
  /* .btn-outline-warning {
    color: #8bc34a;
    border-color: #ffeb3b;
  }
  .btn-outline-warning:hover {
    background: #ffeb3b;
    color: #333;
  } */
</style>