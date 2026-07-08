type LocalDateTimeString = string;

export interface Concert {
    id: number; 
    
    venue: string;

    city: string;

    date: LocalDateTimeString;

    ticketPrice: number;

    ticketLink: string;

}


