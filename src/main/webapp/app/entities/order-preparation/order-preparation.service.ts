import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderPreparation } from 'app/shared/model/order-preparation.model';

type EntityResponseType = HttpResponse<IOrderPreparation>;
type EntityArrayResponseType = HttpResponse<IOrderPreparation[]>;

@Injectable({ providedIn: 'root' })
export class OrderPreparationService {
    public resourceUrl = SERVER_API_URL + 'api/order-preparations';

    constructor(private http: HttpClient) {}

    create(orderPreparation: IOrderPreparation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(orderPreparation);
        return this.http
            .post<IOrderPreparation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(orderPreparation: IOrderPreparation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(orderPreparation);
        return this.http
            .put<IOrderPreparation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IOrderPreparation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IOrderPreparation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(orderPreparation: IOrderPreparation): IOrderPreparation {
        const copy: IOrderPreparation = Object.assign({}, orderPreparation, {
            dateOrder:
                orderPreparation.dateOrder != null && orderPreparation.dateOrder.isValid() ? orderPreparation.dateOrder.toJSON() : null,
            dateDelivery:
                orderPreparation.dateDelivery != null && orderPreparation.dateDelivery.isValid()
                    ? orderPreparation.dateDelivery.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateOrder = res.body.dateOrder != null ? moment(res.body.dateOrder) : null;
            res.body.dateDelivery = res.body.dateDelivery != null ? moment(res.body.dateDelivery) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((orderPreparation: IOrderPreparation) => {
                orderPreparation.dateOrder = orderPreparation.dateOrder != null ? moment(orderPreparation.dateOrder) : null;
                orderPreparation.dateDelivery = orderPreparation.dateDelivery != null ? moment(orderPreparation.dateDelivery) : null;
            });
        }
        return res;
    }
}
