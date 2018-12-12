import { IDish } from 'app/shared/model//dish.model';

export interface IKitchen {
    id?: number;
    type?: string;
    name?: string;
    description?: string;
    dishes?: IDish[];
}

export class Kitchen implements IKitchen {
    constructor(public id?: number, public type?: string, public name?: string, public description?: string, public dishes?: IDish[]) {}
}
