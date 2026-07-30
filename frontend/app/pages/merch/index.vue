<script setup lang="ts">

import type { Merch } from '~/types/merch';

const config = useRuntimeConfig()
const { status, data: merch } = await useFetch<Merch[]>(config.public.apiBase + '/merch')

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

                    <NuxtLink :to="`/merch/${product.id}`">
                    <div>{{ product.merchType }}</div>
                    <div v-if="product.size">{{ product.size }}</div>
                    <div>{{ formatCurrency(product.price) }}</div>
                    <div v-if="!product.inStock" class="text-red-600 font-bold">SOLD OUT</div>
                    <br>
                    </NuxtLink>
            
                

            </div>


        </div>

    </div>

</template>