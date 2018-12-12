import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICooker } from 'app/shared/model/cooker.model';
import { CookerService } from './cooker.service';
import { IKitchen } from 'app/shared/model/kitchen.model';
import { KitchenService } from 'app/entities/kitchen';

@Component({
    selector: 'jhi-cooker-update',
    templateUrl: './cooker-update.component.html'
})
export class CookerUpdateComponent implements OnInit {
    cooker: ICooker;
    isSaving: boolean;

    cookers: IKitchen[];
    birthday: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private cookerService: CookerService,
        private kitchenService: KitchenService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cooker }) => {
            this.cooker = cooker;
            this.birthday = this.cooker.birthday != null ? this.cooker.birthday.format(DATE_TIME_FORMAT) : null;
        });
        this.kitchenService.query({ filter: 'cooker-is-null' }).subscribe(
            (res: HttpResponse<IKitchen[]>) => {
                if (!this.cooker.cookerId) {
                    this.cookers = res.body;
                } else {
                    this.kitchenService.find(this.cooker.cookerId).subscribe(
                        (subRes: HttpResponse<IKitchen>) => {
                            this.cookers = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.cooker.birthday = this.birthday != null ? moment(this.birthday, DATE_TIME_FORMAT) : null;
        if (this.cooker.id !== undefined) {
            this.subscribeToSaveResponse(this.cookerService.update(this.cooker));
        } else {
            this.subscribeToSaveResponse(this.cookerService.create(this.cooker));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICooker>>) {
        result.subscribe((res: HttpResponse<ICooker>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
