import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICooker } from 'app/shared/model/cooker.model';

@Component({
    selector: 'jhi-cooker-detail',
    templateUrl: './cooker-detail.component.html'
})
export class CookerDetailComponent implements OnInit {
    cooker: ICooker;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cooker }) => {
            this.cooker = cooker;
        });
    }

    previousState() {
        window.history.back();
    }
}
