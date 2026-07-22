export default defineNuxtConfig({
  modules: ['@nuxtjs/tailwindcss'],
  

  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },

  runtimeConfig: {
    public: {
      apiBase: 'http://localhost:8080/api'
    }
  }
})
