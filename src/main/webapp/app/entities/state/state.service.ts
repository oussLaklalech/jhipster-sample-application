import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IState } from 'app/shared/model/state.model';

type EntityResponseType = HttpResponse<IState>;
type EntityArrayResponseType = HttpResponse<IState[]>;

@Injectable({ providedIn: 'root' })
export class StateService {
    public resourceUrl = SERVER_API_URL + 'api/states';

    constructor(private http: HttpClient) {}

    create(state: IState): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(state);
        return this.http
            .post<IState>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(state: IState): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(state);
        return this.http
            .put<IState>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IState>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IState[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(state: IState): IState {
        const copy: IState = Object.assign({}, state, {
            date: state.date != null && state.date.isValid() ? state.date.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((state: IState) => {
                state.date = state.date != null ? moment(state.date) : null;
            });
        }
        return res;
    }
}
