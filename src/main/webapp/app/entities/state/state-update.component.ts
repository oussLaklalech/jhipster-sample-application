import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IState } from 'app/shared/model/state.model';
import { StateService } from './state.service';
import { IOrderPreparation } from 'app/shared/model/order-preparation.model';
import { OrderPreparationService } from 'app/entities/order-preparation';

@Component({
    selector: 'jhi-state-update',
    templateUrl: './state-update.component.html'
})
export class StateUpdateComponent implements OnInit {
    state: IState;
    isSaving: boolean;

    orderpreparations: IOrderPreparation[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private stateService: StateService,
        private orderPreparationService: OrderPreparationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ state }) => {
            this.state = state;
            this.date = this.state.date != null ? this.state.date.format(DATE_TIME_FORMAT) : null;
        });
        this.orderPreparationService.query().subscribe(
            (res: HttpResponse<IOrderPreparation[]>) => {
                this.orderpreparations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.state.date = this.date != null ? moment(this.date, DATE_TIME_FORMAT) : null;
        if (this.state.id !== undefined) {
            this.subscribeToSaveResponse(this.stateService.update(this.state));
        } else {
            this.subscribeToSaveResponse(this.stateService.create(this.state));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IState>>) {
        result.subscribe((res: HttpResponse<IState>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackOrderPreparationById(index: number, item: IOrderPreparation) {
        return item.id;
    }
}
