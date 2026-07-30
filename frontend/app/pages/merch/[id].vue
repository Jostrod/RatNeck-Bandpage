<script setup lang="ts">

    import { useRoute } from 'vue-router';
    import type { Merch } from '~/types/merch';

    const route = useRoute()
    const config = useRuntimeConfig()
    const id = route.params.id
    const { status, data: merch } = await useFetch<Merch>(config.public.apiBase + '/merch/' + id)
    


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
        <div>{{ merch.size }}</div>
        <div v-if="!merch.inStock" class="text-red-600 font-bold">SOLD OUT</div>
        <div>{{ formatCurrency(merch.price) }}</div>
    </div>

</template>