<script setup lang="ts">

const props = defineProps<{
    variant: ('primary' | 'secondary' | 'danger')

}>()

const variantClasses = computed(() => {
    const map = {
        primary: 'bg-primary text-text-main',
        secondary: 'bg-gray-600 text-text-main',
        danger: 'bg-red-600 text-text-main'
    }
    return map[props.variant]
})

const baseClasses = 'px-3 py-2 rounded font-body transition-colors disabled:opacity-50 disabled:cursor-not-allowed'


</script>



<template>

    <button :class="[baseClasses, variantClasses]">
        <slot />
    </button>
</template>