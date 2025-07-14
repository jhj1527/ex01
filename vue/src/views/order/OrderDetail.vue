<script setup>

</script>

<template>
  <div class="container my-5">
    <!-- 주문 상품 -->
    <h5 class="fw-bold mb-3">주문 상품</h5>
    <table class="table align-middle">
      <thead>
        <tr>
          <th style="width: 120px;"></th>
          <th>상품명</th>
          <th>수량</th>
          <th>할인금액</th>
          <th>총 결제금액</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>
            <img src="../../assets/image/golden-retriever-puppy-amber-9661916_640.jpg" class="img-thumbnail" alt="상품1">
          </td>
          <td>[CQ5569] Codem Leggin...</td>
          <td>1</td>
          <td>₩1,000</td>
          <td>₩35,900</td>
        </tr>
      </tbody>
    </table>

    <div class="row mt-4">
      <!-- 주문자 정보 -->
      <div class="col-md-6">
        <h6 class="fw-bold mb-3">주문자 정보</h6>
        <table class="table table-border">
          <tbody>
            <tr>
              <td class="text-secondary">주문번호</td>
              <td class="text-end">₩36,900</td>
            </tr>
            <tr>
              <td class="text-secondary">주문일</td>
              <td class="text-end">₩36,900</td>
            </tr>
            <tr>
              <td class="text-secondary">이름</td>
              <td class="text-end">1920P</td>
            </tr>
            <tr>
              <td class="text-secondary">휴대폰번호</td>
              <td class="text-end">₩2,000</td>
            </tr>
            <tr>
              <td class="text-secondary">이메일</td>
              <td class="text-end">1920P</td>
            </tr>
          </tbody>
        </table>
        <!-- <div class="border rounded p-3">
          <div class="row mb-2">
            <div class="col-4 text-secondary">주문번호</div>
            <div class="col-8">1881</div>
          </div>
          <div class="row mb-2">
            <div class="col-4 text-secondary">주문일</div>
            <div class="col-8">23.10.06</div>
          </div>
          <div class="row mb-2">
            <div class="col-4 text-secondary">이름</div>
            <div class="col-8">홍길동</div>
          </div>
          <div class="row mb-2">
            <div class="col-4 text-secondary">휴대폰번호</div>
            <div class="col-8">01012345678</div>
          </div>
          <div class="row">
            <div class="col-4 text-secondary">이메일</div>
            <div class="col-8">honggildong@email.com</div>
          </div>
        </div> -->
      </div>

      <!-- 결제 정보 -->
      <div class="col-md-6">
        <h6 class="fw-bold mb-3">결제 정보</h6>
        <table class="table table-border">
          <tbody>
            <tr>
              <td class="text-secondary">상품금액</td>
              <td class="text-end">₩36,900</td>
            </tr>
            <tr>
              <td class="text-secondary">할인 금액</td>
              <td class="text-end text-danger">₩0</td>
            </tr>
            <tr>
              <td class="text-secondary">적립예정 포인트</td>
              <td class="text-end">1920P</td>
            </tr>
            <tr>
              <td class="text-secondary">배송비</td>
              <td class="text-end">₩2,000</td>
            </tr>
            <tr>
              <td class="fw-bold">총 결제 금액</td>
              <td class="text-end fw-bold text-danger">₩38,900</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="row mt-4">
      <!-- 배송지 정보 -->
      <div class="col-md-6">
        <h6 class="fw-bold mb-3">배송지 정보</h6>
        <div class="border rounded p-3">
          <div>홍길동</div>
          <div>01012345678</div>
          <div>(08504) 서울특별시 금천구 서부샛길 606 (가산동) 대성디폴리스지식산업센터 1810호</div>
          <button class="btn btn-danger btn-sm mt-2">배송지 변경</button>
        </div>
      </div>
    </div>

    <!-- <div class="d-flex justify-content-center gap-2 mt-4">
      <button class="btn btn-outline-secondary">주문상세 보기</button>
      <button class="btn btn-danger">계속 쇼핑하기</button>
    </div> -->
  </div>
</template>