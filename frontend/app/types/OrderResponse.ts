import type { OrderLineResponse } from "./OrderLineResponse";

export interface OrderResponse {

    id: number, 
    createdAt: string,
    lineDTOs: OrderLineResponse[],
    total: number
    

}