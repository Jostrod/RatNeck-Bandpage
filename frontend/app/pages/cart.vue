<script setup lang="ts">

    
    import type { Merch } from '~/types/merch';
    import type { OrderResponse } from '~/types/OrderResponse';

    interface CartLine {
        quantity: number,
        product: Merch
    }
    const errorMessage = ref('')
    const isSubmitting = ref(false)
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
        isSubmitting.value = true

        const body = { lines: cart.items.map(item => ({ productId: item.id, quantity: item.quantity })) }
        
        try {

        const response = await $fetch<OrderResponse>(config.public.apiBase + '/orders', {
            method: 'POST',
            body: body
            })
            cart.setLastOrder(response)
            cart.clearItems()
            await navigateTo('/receipt')
            
        }
        catch(e) {
            errorMessage.value = 'An error occurred' 
            }
        finally {
            isSubmitting.value = false
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

        <BaseButton variant="secondary" @click="cart.addItem(item.product.id)">Add 1</BaseButton>
        
        <BaseButton variant="secondary" @click="cart.decreaseItem(item.product.id)">Remove 1</BaseButton>
        
        <BaseButton variant="danger" @click="cart.deleteItem(item.product.id)">Delete</BaseButton>
        <br>
    </div>

    <div v-if="cartLines.length !== 0 ">Total price: {{ formatCurrency(totalPrice) }}</div>
    <div v-if="cartLines.length !== 0 ">
        
        <BaseButton variant="primary" @click="checkout" :disabled="isSubmitting">Complete purchase</BaseButton>
    
    </div>

    <div v-if="errorMessage">
        {{ errorMessage }}
    </div>
    


</template>