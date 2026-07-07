<script setup>
import SvgLogo from './SvgLogo.vue';
</script>


<template>
    <nav class="navbar">
    <div class="nav-group nav-group-left">
      <NuxtLink to="/merch">Merch</NuxtLink>
      <NuxtLink to="/concerts">Concerts</NuxtLink>
    </div>

    <NuxtLink to="/" class="logo-link">
    <SvgLogo class="logo" />
    </NuxtLink>

    <div class="nav-group nav-group-right">
      <NuxtLink to="/news">News</NuxtLink>
      <NuxtLink to="/contact">Contact</NuxtLink>
    </div>
</nav>

</template>

<style scoped>

.navbar{
    display: grid;
    grid-template-columns: 1fr auto 1fr;
    grid-template-rows: 1fr auto;
    align-items: center;
    width: 100%;
    min-height: 220px;;
    
}

.nav-group{
    display: flex;
    align-items: center;
    gap: 1.25rem;
    flex-wrap: wrap;
    grid-row: 2;
}

.nav-group-left {
    grid-column: 1;
    justify-content: space-evenly;
}

.nav-group-right {
    grid-column: 3;
    justify-content: space-evenly;
}


.logo {
    
    height: 300px;
    align-self: center;
    justify-self: center;
   
}

.logo-link {
    grid-column: 2;
    grid-row: 1;
}



</style>