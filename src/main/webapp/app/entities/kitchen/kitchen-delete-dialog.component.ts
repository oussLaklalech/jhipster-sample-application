import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKitchen } from 'app/shared/model/kitchen.model';
import { KitchenService } from './kitchen.service';

@Component({
    selector: 'jhi-kitchen-delete-dialog',
    templateUrl: './kitchen-delete-dialog.component.html'
})
export class KitchenDeleteDialogComponent {
    kitchen: IKitchen;

    constructor(private kitchenService: KitchenService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kitchenService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'kitchenListModification',
                content: 'Deleted an kitchen'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-kitchen-delete-popup',
    template: ''
})
export class KitchenDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ kitchen }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(KitchenDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.kitchen = kitchen;
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
