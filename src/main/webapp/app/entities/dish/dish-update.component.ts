import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDish } from 'app/shared/model/dish.model';
import { DishService } from './dish.service';
import { IKitchen } from 'app/shared/model/kitchen.model';
import { KitchenService } from 'app/entities/kitchen';

@Component({
    selector: 'jhi-dish-update',
    templateUrl: './dish-update.component.html'
})
export class DishUpdateComponent implements OnInit {
    dish: IDish;
    isSaving: boolean;

    kitchens: IKitchen[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private dishService: DishService,
        private kitchenService: KitchenService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dish }) => {
            this.dish = dish;
        });
        this.kitchenService.query().subscribe(
            (res: HttpResponse<IKitchen[]>) => {
                this.kitchens = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dish.id !== undefined) {
            this.subscribeToSaveResponse(this.dishService.update(this.dish));
        } else {
            this.subscribeToSaveResponse(this.dishService.create(this.dish));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDish>>) {
        result.subscribe((res: HttpResponse<IDish>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackKitchenById(index: number, item: IKitchen) {
        return item.id;
    }
}
