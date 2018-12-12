import { IOrderPreparation } from 'app/shared/model//order-preparation.model';
import { IComment } from 'app/shared/model//comment.model';

export interface IDish {
    id?: number;
    price?: number;
    name?: string;
    description?: string;
    note?: number;
    kitchenId?: number;
    orders?: IOrderPreparation[];
    comments?: IComment[];
}

export class Dish implements IDish {
    constructor(
        public id?: number,
        public price?: number,
        public name?: string,
        public description?: string,
        public note?: number,
        public kitchenId?: number,
        public orders?: IOrderPreparation[],
        public comments?: IComment[]
    ) {}
}
