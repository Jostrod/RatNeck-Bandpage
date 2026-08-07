import type { OrderResponse } from "~/types/OrderResponse";
export interface CartItem {

  id: number;
  quantity: number;

}

export const useCartStore = defineStore("cart", {
  state: () => ({
    items: [] as CartItem[],
    lastOrder: null as OrderResponse | null
  }),

  getters: {
      totalItems(state) {
          return state.items.reduce((sum, line) => sum + line.quantity, 0)
      } 
    },
  

  actions: {
    addItem(id: number) {
      const exists = this.items.find((item) => item.id === id);
      if (!exists) {
        this.items.push({ id: id, quantity: 1 });
      } else {
        exists.quantity += 1;
      }
    },

    deleteItem(id: number) {
      this.items = this.items.filter((item) => item.id !== id);
    },

    decreaseItem(id: number) {
      const exists = this.items.find((item) => item.id === id);

      if (!exists) {
        return;
      }

      if (exists.quantity !== 1) {
        exists.quantity -= 1;
      } else {
        this.deleteItem(id);
        }
      },

      setLastOrder(order: OrderResponse){
        this.lastOrder = order 
      },

      clearItems() {
        this.items = []
      }
    }
  }
);
