import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderPreparation } from 'app/shared/model/order-preparation.model';

@Component({
    selector: 'jhi-order-preparation-detail',
    templateUrl: './order-preparation-detail.component.html'
})
export class OrderPreparationDetailComponent implements OnInit {
    orderPreparation: IOrderPreparation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orderPreparation }) => {
            this.orderPreparation = orderPreparation;
        });
    }

    previousState() {
        window.history.back();
    }
}
