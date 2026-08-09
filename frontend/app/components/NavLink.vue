<script setup lang="ts">

defineProps<{
    to: string
}>()
</script>


<template>
    <NuxtLink :to="to" class="p-2 mb-1 relative group hover:bg-black/10 transition duration-500 hover:scale-105 hover:translate-x-1 
                            hover:-translate-y-0.5 hover:shadow-sm hover:shadow-black">
                                    <slot />
        <!--Top line and right vertical line  -->
        <span class="absolute right-0 top-0 w-0 h-[1px] bg-black transition-all duration-150 group-hover:w-full"></span>
        <span class="absolute right-0 top-0 w-[1px] h-0 bg-black transition-all duration-150 group-hover:h-full"></span>
        
        <!--Bottom line and left vertical line -->
        <span class="absolute left-0 bottom-0 w-0 h-[1px] bg-black transition-all duration-150 group-hover:w-full rounded-full"></span>
        <span class="absolute left-0 bottom-0 w-[1px] h-0 bg-black transition-all duration-150 group-hover:h-full"></span>


      </NuxtLink>
        

</template>