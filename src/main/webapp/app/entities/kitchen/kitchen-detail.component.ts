import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKitchen } from 'app/shared/model/kitchen.model';

@Component({
    selector: 'jhi-kitchen-detail',
    templateUrl: './kitchen-detail.component.html'
})
export class KitchenDetailComponent implements OnInit {
    kitchen: IKitchen;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ kitchen }) => {
            this.kitchen = kitchen;
        });
    }

    previousState() {
        window.history.back();
    }
}
