<script setup lang="ts">

    import type { Merch } from '~/types/merch';

    interface CartLine {
        quantity: number,
        product: Merch
    }
    const errorMessage = ref('')
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

    
    const checkout = async() => {
        const body = { lines: cart.items.map(item => ({ productId: item.id, quantity: item.quantity })) }
        
        try {

        const response = await $fetch(config.public.apiBase + '/orders', {
            method: 'POST',
            body: body
            })
        }
     catch(e) {
            errorMessage.value = 'An error occured' 
        }
    }
</script>


<template>
    

    <div v-if="cartLines.length === 0">
        Your cart is empty
    </div>

    
    <div v-for="item in cartLines" :key="item.product.id">
        
        <div>Item: {{ item.product.merchType }}</div>
        <div>quantity: {{ item.quantity }}</div>
        <div>Price: {{ formatCurrency(item.product.price) }}</div>
        <button @click="cart.addItem(item.product.id)" class="text-fg-brand bg-neutral-primary border border-brand hover:bg-brand focus:ring-4 focus:ring-brand-subtle font-medium leading-5 rounded-base text-xs px-3 py-1.5 focus:outline-none">Add 1</button>
        <button @click="cart.decreaseItem(item.product.id)" class="bg-neutral-primary border border-danger hover:bg-danger hover:text-blue focus:ring-4 focus:ring-neutral-tertiary font-medium leading-5 rounded-base text-sm px-4 py-2.5 focus:outline-none">Remove 1</button>
        <button @click="cart.deleteItem(item.product.id)" class="bg-red-500">Delete</button>
        <br>
    </div>

    <div class="px-3 py-2" v-if="cartLines.length !== 0 ">Total price: {{ formatCurrency(totalPrice) }}</div>
    <div class="py-1 px-2" v-if="cartLines.length !== 0 ">
        <button @click="checkout" class="bg-green-400 rounded-full hover:bg-green-500 py-2 px-2">Complete purchase</button>
    </div>

    <div v-if="errorMessage">
        {{ errorMessage }}
    </div>
    


</template>