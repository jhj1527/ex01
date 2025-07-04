<script setup>
  import { ref, computed } from 'vue'

  const cartItems = ref([
    {
      id: 1,
      name: 'Big Banana',
      price: 30000,
      qty: 1,
      img: 'https://images.pexels.com/photos/461208/pexels-photo-461208.jpeg?auto=compress&w=70&h=70'
    },
    {
      id: 2,
      name: 'Potatoes',
      price: 400000,
      qty: 1,
      img: 'https://images.pexels.com/photos/162825/pexels-photo-162825.jpeg?auto=compress&w=70&h=70'
    },
    {
      id: 3,
      name: 'Awesome Broccoli',
      price: 2.99,
      qty: 1,
      img: 'https://images.pexels.com/photos/839725/pexels-photo-839725.jpeg?auto=compress&w=70&h=70'
    }
  ])

  const coupon = ref('')

  const subtotal = computed(() =>
    cartItems.value.reduce((sum, item) => sum + item.price * item.qty, 0)
  )
  const shipping = 3.0
  const total = computed(() => subtotal.value + shipping)

  function increaseQty(idx) {
    cartItems.value[idx].qty++
  }
  function decreaseQty(idx) {
    if (cartItems.value[idx].qty > 1) cartItems.value[idx].qty--
  }
  function removeItem(idx) {
    cartItems.value.splice(idx, 1)
  }
  function applyCoupon() {
    // Coupon logic here
    alert('Coupon applied: ' + coupon.value)
  }
</script>

<template>
  <div class="container my-5">
    <div class="row">
      <!-- Cart Table -->
      <div class="col-lg-8">
        <table class="table align-middle">
          <thead>
            <tr>
              <th scope="col">
          <input type="checkbox"
                 :checked="cartItems.length && cartItems.every(item => item.checked)"
                 @change="toggleAll($event)">
              </th>
              <th scope="col">Products</th>
              <th scope="col">Name</th>
              <th scope="col">Price</th>
              <th scope="col">Quantity</th>
              <th scope="col">Total</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, idx) in cartItems" :key="item.id">
              <td>
                <input type="checkbox" v-model="item.checked">
              </td>
              <td>
                <img :src="item.img" alt="" class="rounded" style="width:70px; height:70px; object-fit:cover;">
              </td>
              <td>{{ item.name }}</td>
              <td>{{ item.price.toFixed(2) }} $</td>
              <td>
                <div class="d-flex align-items-center">
                  <button class="btn btn-outline-secondary btn-sm rounded-circle me-2"
                    @click="decreaseQty(idx)">
                    <i class="bi bi-dash"></i>
                  </button>
                  <span>{{ item.qty }}</span>
                  <button class="btn btn-outline-secondary btn-sm rounded-circle ms-2"
                    @click="increaseQty(idx)">
                    <i class="bi bi-plus"></i>
                  </button>
                </div>
              </td>
              <td>{{ (item.price * item.qty).toFixed(2) }} $</td>
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
            <span>Subtotal:</span>
            <span>${{ subtotal.toFixed(2) }}</span>
          </div>
          <div class="mb-2">
            <div class="d-flex justify-content-between">
              <span>Shipping</span>
              <span>Flat rate: $3.00</span>
            </div>
            <small class="text-muted ms-0">Shipping to Ukraine.</small>
          </div>
          <hr>
          <div class="d-flex justify-content-between mb-4">
            <span class="fw-bold">Total</span>
            <span class="fw-bold">${{ total.toFixed(2) }}</span>
          </div>
          <button class="btn btn-outline-warning w-100 py-2 fw-bold">
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