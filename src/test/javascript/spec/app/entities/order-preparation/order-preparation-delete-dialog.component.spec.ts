/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OrderPreparationDeleteDialogComponent } from 'app/entities/order-preparation/order-preparation-delete-dialog.component';
import { OrderPreparationService } from 'app/entities/order-preparation/order-preparation.service';

describe('Component Tests', () => {
    describe('OrderPreparation Management Delete Component', () => {
        let comp: OrderPreparationDeleteDialogComponent;
        let fixture: ComponentFixture<OrderPreparationDeleteDialogComponent>;
        let service: OrderPreparationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [OrderPreparationDeleteDialogComponent]
            })
                .overrideTemplate(OrderPreparationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrderPreparationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderPreparationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
