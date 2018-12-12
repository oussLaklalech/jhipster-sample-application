/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OrderPreparationComponent } from 'app/entities/order-preparation/order-preparation.component';
import { OrderPreparationService } from 'app/entities/order-preparation/order-preparation.service';
import { OrderPreparation } from 'app/shared/model/order-preparation.model';

describe('Component Tests', () => {
    describe('OrderPreparation Management Component', () => {
        let comp: OrderPreparationComponent;
        let fixture: ComponentFixture<OrderPreparationComponent>;
        let service: OrderPreparationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [OrderPreparationComponent],
                providers: []
            })
                .overrideTemplate(OrderPreparationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrderPreparationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderPreparationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new OrderPreparation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.orderPreparations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
