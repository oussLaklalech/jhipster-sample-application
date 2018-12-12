import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOrderPreparation } from 'app/shared/model/order-preparation.model';
import { Principal } from 'app/core';
import { OrderPreparationService } from './order-preparation.service';

@Component({
    selector: 'jhi-order-preparation',
    templateUrl: './order-preparation.component.html'
})
export class OrderPreparationComponent implements OnInit, OnDestroy {
    orderPreparations: IOrderPreparation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private orderPreparationService: OrderPreparationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.orderPreparationService.query().subscribe(
            (res: HttpResponse<IOrderPreparation[]>) => {
                this.orderPreparations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOrderPreparations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOrderPreparation) {
        return item.id;
    }

    registerChangeInOrderPreparations() {
        this.eventSubscriber = this.eventManager.subscribe('orderPreparationListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
