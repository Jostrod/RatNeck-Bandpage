<script setup lang="ts">
import { ref } from 'vue';

const errorMessage = ref('')



async function logout(){

    errorMessage.value = ''

    try {
            await $fetch('http://localhost:8080/logout', {
            method: 'POST',
            credentials: 'include',
        })
        
       await navigateTo("/")

    } catch(error) {
            errorMessage.value = "Something went wrong"
            console.error(error)
    }
}
   


</script>

<template>
   <BaseButton variant="secondary" @click="logout">
    Log out    
</BaseButton>

<div v-if="errorMessage">
        {{ errorMessage }}
    </div>

</template>


