/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { OrderPreparationDetailComponent } from 'app/entities/order-preparation/order-preparation-detail.component';
import { OrderPreparation } from 'app/shared/model/order-preparation.model';

describe('Component Tests', () => {
    describe('OrderPreparation Management Detail Component', () => {
        let comp: OrderPreparationDetailComponent;
        let fixture: ComponentFixture<OrderPreparationDetailComponent>;
        const route = ({ data: of({ orderPreparation: new OrderPreparation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [OrderPreparationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrderPreparationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrderPreparationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.orderPreparation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
