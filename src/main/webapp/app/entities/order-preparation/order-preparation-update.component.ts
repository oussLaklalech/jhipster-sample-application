import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IOrderPreparation } from 'app/shared/model/order-preparation.model';
import { OrderPreparationService } from './order-preparation.service';
import { IConsumer } from 'app/shared/model/consumer.model';
import { ConsumerService } from 'app/entities/consumer';
import { IDish } from 'app/shared/model/dish.model';
import { DishService } from 'app/entities/dish';

@Component({
    selector: 'jhi-order-preparation-update',
    templateUrl: './order-preparation-update.component.html'
})
export class OrderPreparationUpdateComponent implements OnInit {
    orderPreparation: IOrderPreparation;
    isSaving: boolean;

    consumers: IConsumer[];

    dishes: IDish[];
    dateOrder: string;
    dateDelivery: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private orderPreparationService: OrderPreparationService,
        private consumerService: ConsumerService,
        private dishService: DishService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orderPreparation }) => {
            this.orderPreparation = orderPreparation;
            this.dateOrder = this.orderPreparation.dateOrder != null ? this.orderPreparation.dateOrder.format(DATE_TIME_FORMAT) : null;
            this.dateDelivery =
                this.orderPreparation.dateDelivery != null ? this.orderPreparation.dateDelivery.format(DATE_TIME_FORMAT) : null;
        });
        this.consumerService.query().subscribe(
            (res: HttpResponse<IConsumer[]>) => {
                this.consumers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.dishService.query().subscribe(
            (res: HttpResponse<IDish[]>) => {
                this.dishes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.orderPreparation.dateOrder = this.dateOrder != null ? moment(this.dateOrder, DATE_TIME_FORMAT) : null;
        this.orderPreparation.dateDelivery = this.dateDelivery != null ? moment(this.dateDelivery, DATE_TIME_FORMAT) : null;
        if (this.orderPreparation.id !== undefined) {
            this.subscribeToSaveResponse(this.orderPreparationService.update(this.orderPreparation));
        } else {
            this.subscribeToSaveResponse(this.orderPreparationService.create(this.orderPreparation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrderPreparation>>) {
        result.subscribe((res: HttpResponse<IOrderPreparation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackConsumerById(index: number, item: IConsumer) {
        return item.id;
    }

    trackDishById(index: number, item: IDish) {
        return item.id;
    }
}
