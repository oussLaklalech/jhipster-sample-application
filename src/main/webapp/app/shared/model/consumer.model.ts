import { Moment } from 'moment';
import { IOrderPreparation } from 'app/shared/model//order-preparation.model';
import { IComment } from 'app/shared/model//comment.model';

export interface IConsumer {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    phoneNumber?: string;
    birthday?: Moment;
    address?: string;
    orders?: IOrderPreparation[];
    comments?: IComment[];
}

export class Consumer implements IConsumer {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phoneNumber?: string,
        public birthday?: Moment,
        public address?: string,
        public orders?: IOrderPreparation[],
        public comments?: IComment[]
    ) {}
}
