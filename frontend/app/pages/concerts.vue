

<script setup lang="ts">

     import type { Concert } from '~/types/concert'; 

    const config = useRuntimeConfig()
    const { error, data: concerts } = await useFetch<Concert[]>(config.public.apiBase +'/concerts')

</script>

<template>

    <div>

        <div v-if="error">Failed to load data.</div>

    <div class="grid grid-cols-5 px-10 pt-9">
        <div class="text-center border border-black p-1">Venue:</div>
        <div class="text-center border border-black p-1">City:</div>
        <div class="text-center border border-black p-1">Date:</div>
        <div class="text-center border border-black p-1">Ticket Price:</div>
        <div class="text-center border border-black p-1">Ticket Link:</div>
        
    </div>

    <div class="grid grid-cols-1 gap-3 py-5 px-10">
    
            <div class="grid grid-cols-5 " v-for="concert in concerts" :key="concert.id"
            >
                <div class="border border-black p-1 text-center">{{ concert.venue }}</div>
                <div class="border border-black p-1 text-center">{{ concert.city }}</div>
                <div class="border border-black p-1 text-center">{{ concert.date }}</div>
                <div class="border border-black p-1 text-center">{{ concert.ticketPrice }}</div>
                <div class="border border-black p-1 text-center">{{ concert.ticketLink }}</div>
    </div>
            
        
    </div>
        
    </div>


</template>