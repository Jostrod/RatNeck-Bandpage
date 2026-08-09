<script setup lang="ts">

defineProps<{
    to: string
}>()
</script>


<template>
    <NuxtLink :to="to" class="relative group"><slot />
        <!--Top line and right vertical line  -->
        <span class="absolute right-0 top-0 w-0 h-[1px] bg-black transition-all duration-200 group-hover:w-full "></span>
        <span class="absolute right-0 top-0 w-[1px] h-0 bg-black transition-all duration-200 group-hover:h-full"></span>
        
        <!--Bottom line and left vertical line -->
        <span class="absolute left-0 bottom-0 w-0 h-[1px] bg-black transition-all duration-200 group-hover:w-full rounded-full"></span>
        <span class="absolute left-0 bottom-0 w-[1px] h-0 bg-black transition-all duration-200 group-hover:h-full"></span>


      </NuxtLink>
        

</template>