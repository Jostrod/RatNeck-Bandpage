<script setup lang="ts">
const cart = useCartStore()




</script>


<template>


    <div v-if="cart.lastOrder">
        <h1>This is your receipt, thx for cash!</h1>
        <div v-for="line in cart.lastOrder.lineDTOs" :key="line.productId">
            <br>
            {{ line.merchType }}<br>
            {{ "quantity: " + line.quantity }}<br>
            {{ formatCurrency(line.priceAtPurchase) }}<br>
        </div>
        <br>
        {{ "Total price: " + formatCurrency(cart.lastOrder.total) }}
    </div>
    <div v-else>
        "No recent orders to show" 
    </div>
</template>