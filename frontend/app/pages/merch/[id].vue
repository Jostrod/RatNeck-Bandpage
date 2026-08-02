
<script setup lang="ts">

    
    import type { Merch } from '~/types/merch';

    const route = useRoute()
    const config = useRuntimeConfig()
    const id = route.params.id
    const { status, data: merch } = await useFetch<Merch>(config.public.apiBase + '/merch/' + id)
    const cart = useCartStore()
    

</script>


<template>

    <div v-if="status === 'error'">
        Failed to load product
    </div>

    <div v-else-if="status === 'pending'">
        Loading product...
    </div>

    <div v-else-if="merch">
        
        <div>{{ merch.merchType }}</div>
        <div v-if="merch.size">{{ merch.size }}</div>
        <div v-if="!merch.inStock" class="text-red-600 font-bold">SOLD OUT</div>
        <div>{{ formatCurrency(merch.price) }}</div>
        <button :disabled="!merch.inStock" @click="cart.addItem(merch.id)" class="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded disabled:opacity-50 disabled:cursor-not-allowed">Add to basket</button>
        

    </div>

</template>