import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IKitchen } from 'app/shared/model/kitchen.model';
import { KitchenService } from './kitchen.service';

@Component({
    selector: 'jhi-kitchen-update',
    templateUrl: './kitchen-update.component.html'
})
export class KitchenUpdateComponent implements OnInit {
    kitchen: IKitchen;
    isSaving: boolean;

    constructor(private kitchenService: KitchenService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ kitchen }) => {
            this.kitchen = kitchen;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.kitchen.id !== undefined) {
            this.subscribeToSaveResponse(this.kitchenService.update(this.kitchen));
        } else {
            this.subscribeToSaveResponse(this.kitchenService.create(this.kitchen));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IKitchen>>) {
        result.subscribe((res: HttpResponse<IKitchen>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
