export default defineNuxtConfig({
  modules: ['@nuxtjs/tailwindcss', 
            '@pinia/nuxt',
            '@nuxt/icon',
            '@nuxtjs/google-fonts'],
  googleFonts: {
    families: {
      Cinzel: [400, 700],
      Roboto: [400, 700]
    }
  },

  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },

  runtimeConfig: {
    public: {
      apiBase: 'http://localhost:8080/api'
    }
  }
})
