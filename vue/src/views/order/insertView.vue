<script setup>
  import { commonApi } from '@/service/common';
  import { useStore } from '@/stores/store';
  import { storeToRefs } from 'pinia';
  import { computed, onMounted, reactive, ref } from 'vue';
  import { useRoute, useRouter } from 'vue-router';
  
  const route = useRoute();
  const router = useRouter();
  const store = useStore();
  const { member, cart } = storeToRefs(store);

  const result = ref({});
  const input = reactive({
    id : "",
    postCode : "",
    address1 : "",
    address2 : "",
    address3 : "",
    orderPrice : 0,
    charge : 0,
    phone : "",
    email : "",
    list : {}
  });
  let param = {};

  onMounted(() => {
    input.id = member.value.id;
    getCheckList();
  });

  const priceFormat = computed(() => 
    (value) => value > 0 ? value.toLocaleString() : 0
  );
  // const priceFormat = computed(() => {
  //   return price ? price.toLocaleString() : 0;
  // });

  // const subTotal = computed(() => {
  //   return result.value?.reduce((sum, item) => sum + (item.price * item.amount), 0);
  // });

  // const charge = computed(() => {
  //   return subTotal >= 30000 ? 0 : 3000;
  // });

  // const total = computed(() => {
  //   return subTotal + charge;
  // });

  const getCheckList = async () => {
    param = {};
    param.checkArr = cart.value.checkArr;
    const res = await commonApi("/api/cart/checkList", "get", param);
    result.value = res.data.filter(item => delete item.attachList);
    console.log(result.value);

    input.orderPrice = result.value?.reduce((sum, item) => sum + (item.price * item.amount), 0);
    input.charge = input.orderPrice >= 30000 ? 0 : 3000;
  };

  const order = async () => {    
    input.list = result.value;
    let res = await commonApi("/api/order/insert", "post", input);

    if (res.status === 200 || res.status === 201) {
      console.log(res.data);

      param = {};
      param.id = input.id;
      res = await commonApi("/api/cart/getCount", "get", param);
      cart.value.count = res.data;
    }
  };

  const importApi = () => {
    // 포트원 고객사 식별코드
    let ranNum = "";
    let orderNum = "";
    let today = new Date();
    let year = today.getFullYear(); // 년도
    let month = today.getMonth() + 1;  // 월
    let date = today.getDate();  // 날짜

    if (month < 10) {
      month = "0" + month.toString();
    }

    today = year.toString() + month.toString() + date.toString();

    for (let i = 0; i < 6; i++) {
      ranNum = ranNum + Math.floor(Math.random() * 10);
    }

    orderNum = today + "_" + ranNum;

    console.log(orderNum);

    IMP.init("imp48621712");

    IMP.request_pay({
      pg : "html5_inicis", // 실제 계약 후에는 실제 상점아이디로 변경
      pay_method : 'card', // 'card'만 지원됩니다.
      merchant_uid: orderNum, // 상점에서 관리하는 주문 번호
      name : 'shop',
      amount : 100, //input.orderPrice + input.charge, // 결제창에 표시될 금액. 실제 승인이 이루어지지는 않습니다. (모바일에서는 가격이 표시되지 않음)
      // customer_uid : 'your-customer-unique-id', // 필수 입력.
      buyer_email : input.email,
      buyer_name : input.id,
      buyer_tel : input.phone,
      // m_redirect_url : '{모바일에서 결제 완료 후 리디렉션 될 URL}' // 예: https://www.my-service.com/payments/complete/mobile
    }, function(res) {
      if ( res.success ) {
        console.log(res);
        order();
        alert('결제성공');

      } else {
        alert("결제실패");
        console.log(res);
      }
    });
  }

  const PostCodeApi = () => {
    new window.daum.Postcode({
      oncomplete: (data) => {
        if (input.address3 !== "") {
          input.address3 = "";
        }

        if (data.userSelectedType === "R") {
          // 사용자가 도로명 주소를 선택했을 경우
          input.address1 = data.roadAddress;
        } else {
          // 사용자가 지번 주소를 선택했을 경우(J)
          input.address1 = data.jibunAddress;
        }

        // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
        if (data.userSelectedType === "R") {
          // 법정동명이 있을 경우 추가한다. (법정리는 제외)
          // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
          if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
            input.address3 += data.bname;
          }

          // 건물명이 있고, 공동주택일 경우 추가한다.
          if (data.buildingName !== "" && data.apartment === "Y") {
            input.address3 += input.address3 !== "" ? `, ${data.buildingName}` : data.buildingName;
          }

          // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
          if (input.address3 !== "") {
            input.address3 = `(${input.address3})`;
          }

        } else {
          input.address3 = "";
        }
        // 우편번호를 입력한다.
        input.postCode = data.zonecode;
      },
    }).open();
  };
  
</script>

<template>
  <div class="container my-5">
    <div class="row">
      <!-- Billing Details -->
      <div class="col-lg-8">
        <h4 class="fw-bold mb-4">Check out</h4>
        <div class="mb-3">
          <label class="form-label">id<span class="text-danger">*</span></label>
          <input type="text" v-model="input.id" class="form-control" />
        </div>
        <div class="mb-3">
          <label class="form-label">Address<span class="text-danger">*</span></label>
          <div class="d-flex mb-2">
            <input type="text" v-model="input.postCode" class="form-control me-2" style="max-width: 150px;" placeholder="우편번호" />
            <button type="button" @click="PostCodeApi" class="btn btn-outline-secondary" style="min-width: 120px;">우편번호 찾기</button>
          </div>
          <input type="text" v-model="input.address1" class="form-control mb-2" placeholder="도로명주소" />
          <div class="row mb-3">
            <div class="col-md-6">
              <input type="text" v-model="input.address2" class="form-control" placeholder="상세주소" />
            </div>
            <div class="col-md-6">
              <input type="email" v-model="input.address3" class="form-control" placeholder="참고항목" />
            </div>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col-md-6">
            <label class="form-label">Phone<span class="text-danger">*</span></label>
            <input type="text" v-model="input.phone" class="form-control" />
          </div>
          <div class="col-md-6">
            <label class="form-label">Email<span class="text-danger">*</span></label>
            <input type="email" v-model="input.email" class="form-control" />
          </div>
        </div>
      </div>
      <!-- Order Summary -->
      <div class="col-lg-4">
        <div class="bg-light p-4 rounded">
          <h5 class="fw-bold mb-4">Your Order</h5>
          <div class="mb-3">
            <div class="d-flex justify-content-between fw-bold mb-2">
              <span style="flex:2;">Products</span>
              <span style="flex:1; text-align:center;">Qty</span>
              <span style="flex:1; text-align:right;">Total</span>
            </div>
            <div v-for="(item, i) in result" :key="item.cno" class="d-flex justify-content-between align-items-center">
              <span v-html="item.name" style="flex:2;"></span>
              <span v-html="item.amount" style="flex:1; text-align:center;"></span>
              <span v-html="priceFormat(item.price)" class="fw-bold" style="flex:1; text-align:right;"></span>
            </div>
            <hr />
            <div class="d-flex justify-content-between fw-bold">
              <span>Subtotal</span>
              <span>{{ priceFormat(input.orderPrice) }}</span>
            </div>
            <div class="d-flex justify-content-between fw-bold">
              <span>charge</span>
              <span>{{ priceFormat(input.charge) }}</span>
            </div>
            <div class="d-flex justify-content-between fw-bold">
              <span>Total</span>
              <span class="text-danger">{{ priceFormat(input.orderPrice + input.charge) }}</span>
            </div>
          </div>
          <!-- <div class="form-check mb-2">
            <input class="form-check-input" type="checkbox" id="checkPayment" />
            <label class="form-check-label" for="checkPayment">
              cash
            </label>
          </div>
          <div class="form-check mb-4">
            <input class="form-check-input" type="checkbox" id="paypal" />
            <label class="form-check-label" for="paypal">
              card
            </label>
          </div> -->
          <button type="button" @click="importApi" class="btn btn-success w-100 fw-bold py-2" style="font-size: 1.1rem;">
            PLACE ORDER
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Optional: adjust spacing and font to match the image more closely */
.form-label {
  font-weight: 500;
}
</style>