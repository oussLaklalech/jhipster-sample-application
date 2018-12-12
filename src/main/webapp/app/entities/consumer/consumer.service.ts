import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConsumer } from 'app/shared/model/consumer.model';

type EntityResponseType = HttpResponse<IConsumer>;
type EntityArrayResponseType = HttpResponse<IConsumer[]>;

@Injectable({ providedIn: 'root' })
export class ConsumerService {
    public resourceUrl = SERVER_API_URL + 'api/consumers';

    constructor(private http: HttpClient) {}

    create(consumer: IConsumer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(consumer);
        return this.http
            .post<IConsumer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(consumer: IConsumer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(consumer);
        return this.http
            .put<IConsumer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IConsumer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IConsumer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(consumer: IConsumer): IConsumer {
        const copy: IConsumer = Object.assign({}, consumer, {
            birthday: consumer.birthday != null && consumer.birthday.isValid() ? consumer.birthday.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.birthday = res.body.birthday != null ? moment(res.body.birthday) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((consumer: IConsumer) => {
                consumer.birthday = consumer.birthday != null ? moment(consumer.birthday) : null;
            });
        }
        return res;
    }
}
