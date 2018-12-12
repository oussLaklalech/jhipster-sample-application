/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OrderPreparationUpdateComponent } from 'app/entities/order-preparation/order-preparation-update.component';
import { OrderPreparationService } from 'app/entities/order-preparation/order-preparation.service';
import { OrderPreparation } from 'app/shared/model/order-preparation.model';

describe('Component Tests', () => {
    describe('OrderPreparation Management Update Component', () => {
        let comp: OrderPreparationUpdateComponent;
        let fixture: ComponentFixture<OrderPreparationUpdateComponent>;
        let service: OrderPreparationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [OrderPreparationUpdateComponent]
            })
                .overrideTemplate(OrderPreparationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrderPreparationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderPreparationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrderPreparation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orderPreparation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrderPreparation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orderPreparation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
