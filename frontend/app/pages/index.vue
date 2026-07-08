

<script setup lang="ts">
    
    import type { Concert } from '~/types/concert';

    const config = useRuntimeConfig()
    const { error, data: concerts } = await useFetch<Concert[]>(config.public.apiBase +'/concerts')
  

</script>

<template>

    <div>
        <h2>Spring Boot concert List!</h2>

        <div v-if="error">Failed to load data.</div>

        <ul>
            <li  v-for="concert in concerts" :key="concert.id">
                {{ concert.venue }}
                {{ concert.city }}
                {{ concert.date }}
            </li>
            
        </ul>

        
    </div>
   
</template>
