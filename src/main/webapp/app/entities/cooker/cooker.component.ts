import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICooker } from 'app/shared/model/cooker.model';
import { Principal } from 'app/core';
import { CookerService } from './cooker.service';

@Component({
    selector: 'jhi-cooker',
    templateUrl: './cooker.component.html'
})
export class CookerComponent implements OnInit, OnDestroy {
    cookers: ICooker[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private cookerService: CookerService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.cookerService.query().subscribe(
            (res: HttpResponse<ICooker[]>) => {
                this.cookers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCookers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICooker) {
        return item.id;
    }

    registerChangeInCookers() {
        this.eventSubscriber = this.eventManager.subscribe('cookerListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
