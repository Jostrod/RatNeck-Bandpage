
export interface CartItem {
    id: number,

    quantity: number
}

export const useCartStore = defineStore('cart', {
    state: ()=> ({
        items: [] as CartItem[],

        
    }),
    actions: {
        addItem(id: number) {
            const exists = this.items.find(item => item.id === id)
            if(!exists){
                this.items.push({ id: id, quantity: 1 })
            }
            else {
                exists.quantity += 1
            }
        }
    }
})