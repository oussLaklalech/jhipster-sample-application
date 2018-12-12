import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from './comment.service';
import { IConsumer } from 'app/shared/model/consumer.model';
import { ConsumerService } from 'app/entities/consumer';
import { IDish } from 'app/shared/model/dish.model';
import { DishService } from 'app/entities/dish';

@Component({
    selector: 'jhi-comment-update',
    templateUrl: './comment-update.component.html'
})
export class CommentUpdateComponent implements OnInit {
    comment: IComment;
    isSaving: boolean;

    consumers: IConsumer[];

    dishes: IDish[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private commentService: CommentService,
        private consumerService: ConsumerService,
        private dishService: DishService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ comment }) => {
            this.comment = comment;
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
        if (this.comment.id !== undefined) {
            this.subscribeToSaveResponse(this.commentService.update(this.comment));
        } else {
            this.subscribeToSaveResponse(this.commentService.create(this.comment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>) {
        result.subscribe((res: HttpResponse<IComment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
