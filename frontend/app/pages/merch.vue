<script setup lang="ts">

import type { Merch } from '~/types/merch';

const config = useRuntimeConfig()
const { status, data: merch } = await useFetch<Merch[]>(config.public.apiBase + '/merch')



const formatCurrency = (value: number, locale = 'nb-NO') => {

    return new Intl.NumberFormat(locale, {
        style: 'currency',
        currency: 'NOK'
    }).format(value)
}


</script>


<template>

    <div>

        <div v-if="status === 'error'">
            Failed to load products
        </div>

        <div v-else-if="status === 'pending'">
            Loading products...
        </div>

        <div v-else>
            <div v-for="product in merch" :key="product.id">

                <div>{{ product.merchType }}</div>
                <div v-if="product.size">{{ product.size }}</div>
                <div>{{ formatCurrency(product.price) }}</div>
                <div v-if="!product.inStock" class="text-red-600 font-bold">SOLD OUT</div>
                <br>

            </div>


        </div>

    </div>

</template>