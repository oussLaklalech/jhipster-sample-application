export interface IComment {
    id?: number;
    content?: string;
    note?: number;
    consumerId?: number;
    dishId?: number;
}

export class Comment implements IComment {
    constructor(public id?: number, public content?: string, public note?: number, public consumerId?: number, public dishId?: number) {}
}
