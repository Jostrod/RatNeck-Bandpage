

<script setup lang="ts">

     import type { Concert } from '~/types/concert'; 


    const config = useRuntimeConfig()
    const { error, data: concerts } = await useFetch<Concert[]>(config.public.apiBase +'/concerts')

    function formatDate(date: string){
        return new Date(date).toLocaleString('nb-NO',{
            year: 'numeric',
            month: 'long',
            day: 'numeric', 
            hour: 'numeric',
            minute: 'numeric'
        })
    }

</script>

<template>
        <div v-if="error">
            Failed to load data.
        </div>

        

    <div class="grid grid-cols-1 gap-1 px-10 py-5">

        <div class="grid grid-cols-5 divide-x divide-black border border-black mb-3">
                <div class="text-center">Venue:</div>
                <div class="text-center">City:</div>
                <div class="text-center">Date:</div>
                <div class="text-center">Ticket Price:</div>
                <div class="text-center">Ticket Link:</div>
    
        </div>
    
            <div class="grid grid-cols-5 divide-x divide-black border border-black" v-for="concert in concerts" :key="concert.id">

                <div class="text-center ">{{ concert.venue }}</div>
                <div class="text-center">{{ concert.city }}</div>
                <div class="text-center">{{  formatDate(concert.date)}}</div>
                <div class="text-center">{{ concert.ticketPrice }}</div>
                <div class="text-center">
            
                    <a :href="concert.ticketLink" target="_blank" class="inline-block rounded border-2 border-slate-800 text-sm font-medium text-slate-800 transition duration-150 ease-in-out hover:bg-slate-900 hover:text-white focus:outline-none active:bg-slate-700">Kjøp billetter her</a>
                </div>
                <!-- <div class="text-center">{{ concert.ticketLink }}</div>  -->
            </div>   
    </div>
        
    


</template>