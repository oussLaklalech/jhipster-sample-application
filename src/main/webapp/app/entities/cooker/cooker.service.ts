import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICooker } from 'app/shared/model/cooker.model';

type EntityResponseType = HttpResponse<ICooker>;
type EntityArrayResponseType = HttpResponse<ICooker[]>;

@Injectable({ providedIn: 'root' })
export class CookerService {
    public resourceUrl = SERVER_API_URL + 'api/cookers';

    constructor(private http: HttpClient) {}

    create(cooker: ICooker): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cooker);
        return this.http
            .post<ICooker>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(cooker: ICooker): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cooker);
        return this.http
            .put<ICooker>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICooker>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICooker[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(cooker: ICooker): ICooker {
        const copy: ICooker = Object.assign({}, cooker, {
            birthday: cooker.birthday != null && cooker.birthday.isValid() ? cooker.birthday.toJSON() : null
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
            res.body.forEach((cooker: ICooker) => {
                cooker.birthday = cooker.birthday != null ? moment(cooker.birthday) : null;
            });
        }
        return res;
    }
}
