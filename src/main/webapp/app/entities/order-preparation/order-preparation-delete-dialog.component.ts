import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderPreparation } from 'app/shared/model/order-preparation.model';
import { OrderPreparationService } from './order-preparation.service';

@Component({
    selector: 'jhi-order-preparation-delete-dialog',
    templateUrl: './order-preparation-delete-dialog.component.html'
})
export class OrderPreparationDeleteDialogComponent {
    orderPreparation: IOrderPreparation;

    constructor(
        private orderPreparationService: OrderPreparationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderPreparationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'orderPreparationListModification',
                content: 'Deleted an orderPreparation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-preparation-delete-popup',
    template: ''
})
export class OrderPreparationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orderPreparation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OrderPreparationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.orderPreparation = orderPreparation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
