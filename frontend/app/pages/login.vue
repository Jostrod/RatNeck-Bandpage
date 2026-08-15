

<script setup lang="ts">
import { ref } from 'vue';
import { FetchError } from 'ofetch'


const userName = ref('')
const password = ref('')
const errorMessage = ref('')
const successMessage = ref('')

async function handleLogin() {

    errorMessage.value = ''

    try {
        const response = await $fetch('http://localhost:8080/login', {
            method: 'POST',
            credentials: 'include',

            body: new URLSearchParams({
                username: userName.value,
                password: password.value,
            })
        })

        successMessage.value = "Login successful!";
        setTimeout(() => {
            navigateTo("/")
        }, 2000)

    } catch(error) {

        if (error instanceof FetchError) {
            errorMessage.value = error.data?.message || 'Invalid username or password.'
            console.error(error)
        } else{
            errorMessage.value = "Something went wrong"
            console.error(error)

        }
    }
}

</script>


<template>

    <form @submit.prevent="handleLogin">

    <label>Username:
    <input type="text" class="text-black" id="userName" v-model="userName" placeholder="Username">
    </label>

    <br>

    <label>Password:
    <input type="password" class="text-black" id="password" v-model="password" placeholder="password">
    </label>

    <br>

    <BaseButton variant="primary">Log in</BaseButton>

    </form>


    <div v-if="errorMessage">
        {{ errorMessage }}
    </div>

    <div v-if="successMessage">
        {{ successMessage }}
    </div>


    
</template>