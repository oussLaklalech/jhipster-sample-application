import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IConsumer } from 'app/shared/model/consumer.model';
import { ConsumerService } from './consumer.service';

@Component({
    selector: 'jhi-consumer-update',
    templateUrl: './consumer-update.component.html'
})
export class ConsumerUpdateComponent implements OnInit {
    consumer: IConsumer;
    isSaving: boolean;
    birthday: string;

    constructor(private consumerService: ConsumerService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ consumer }) => {
            this.consumer = consumer;
            this.birthday = this.consumer.birthday != null ? this.consumer.birthday.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.consumer.birthday = this.birthday != null ? moment(this.birthday, DATE_TIME_FORMAT) : null;
        if (this.consumer.id !== undefined) {
            this.subscribeToSaveResponse(this.consumerService.update(this.consumer));
        } else {
            this.subscribeToSaveResponse(this.consumerService.create(this.consumer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IConsumer>>) {
        result.subscribe((res: HttpResponse<IConsumer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
