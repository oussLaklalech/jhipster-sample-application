import { Moment } from 'moment';

export interface IState {
    id?: number;
    type?: string;
    date?: Moment;
    orderPreparationId?: number;
}

export class State implements IState {
    constructor(public id?: number, public type?: string, public date?: Moment, public orderPreparationId?: number) {}
}
