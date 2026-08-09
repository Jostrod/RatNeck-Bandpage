<script setup>
import SvgLogo from './SvgLogo.vue';
const cart = useCartStore()
</script>


<template>
  <nav
    class="grid grid-cols-[1fr_auto_1fr] grid-rows-[1fr_auto] items-center w-full min-h-[220px] bg-black/10 border border-black">
    <!-- Cart -->

    <div class="col-start-1 row-start-1 self-start justify-self-start">
      
      <NuxtLink to="/cart" aria-label="cart">
        <div class="relative inline-block bg-blue-500 rounded-full">
          <Icon name="mdi:cart" size="30px" />
          <span
            v-if="cart.totalItems > 0"
            class="absolute bottom-0 right-0 w-5 h-5 flex items-center justify-center text-black font-bold">
            {{ cart.totalItems }}
          </span>
        </div>
      </NuxtLink>
      
    </div>

    <!-- Left nav group -->

    <div
      class="col-start-1 row-start-2 flex items-center gap-5 flex-wrap justify-evenly">
      <NavLink to="/merch">Merch</NavLink>
      <NavLink to="/concerts">Concerts</NavLink>
    </div>

    <!-- Logo -->

    <NuxtLink
      to="/" class=" col-start-2 row-start-1 self-center justify-self-center">
      <SvgLogo class="h-[300px] transition duration-500 hover:-translate-y-1 
                      hover:drop-shadow-[0_5px_5px_#a63e78]" />
    </NuxtLink>

    <!-- Right nav group -->

    <div
      class="col-start-3 row-start-2 flex items-center gap-5 flex-wrap justify-evenly">
      <NavLink to="/news">News</NavLink>
      <NavLink to="/contact">Contact</NavLink>
    </div>
  </nav>
</template>
