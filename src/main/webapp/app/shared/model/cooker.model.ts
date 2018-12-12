import { Moment } from 'moment';

export interface ICooker {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    phoneNumber?: string;
    birthday?: Moment;
    address?: string;
    cookerId?: number;
}

export class Cooker implements ICooker {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phoneNumber?: string,
        public birthday?: Moment,
        public address?: string,
        public cookerId?: number
    ) {}
}
