<script>

import TheNavBar from '~/components/TheNavBar.vue';


</script>

<template>

    <div class="min-h-screen bg-gradient-to-b from-background-to to-background-from text-white">
        <TheNavBar />
        <slot />
    </div>

   

</template>