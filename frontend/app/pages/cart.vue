<script setup lang="ts">

    import type { Merch } from '~/types/merch';

    interface CartLine {
        quantity: number,
        product: Merch
    }

    const cart = useCartStore()
    const config = useRuntimeConfig()
    const { status, data: merch } = await useFetch<Merch[]>(config.public.apiBase + '/merch')

    const cartLines = computed(() => {
        return cart.items.map(item => {
            const product = merch.value?.find(p => p.id === item.id)
            return {
                quantity: item.quantity,
                product: product
            }
        })
        .filter((line): line is CartLine => line.product !== undefined)
    })

    const totalPrice = computed (() => {
        return cartLines.value.reduce((sum, line) => sum + line.product.price * line.quantity, 0)
    })

</script>


<template>
    

    <div v-if="cartLines.length === 0">
        Your cart is empty
    </div>

    
    <div v-for="item in cartLines" :key="item.product.id">
        
        <div>Item: {{ item.product.merchType }}</div>
        <div>quantity: {{ item.quantity }}</div>
        <div>Price: {{ formatCurrency(item.product.price) }}</div>
        <div>Product id: {{ item.product.id }}</div>
        <br>
    </div>
    <div>Total price: {{ formatCurrency(totalPrice) }}</div>
    


</template>