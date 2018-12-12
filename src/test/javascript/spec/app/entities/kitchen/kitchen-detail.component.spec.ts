/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { KitchenDetailComponent } from 'app/entities/kitchen/kitchen-detail.component';
import { Kitchen } from 'app/shared/model/kitchen.model';

describe('Component Tests', () => {
    describe('Kitchen Management Detail Component', () => {
        let comp: KitchenDetailComponent;
        let fixture: ComponentFixture<KitchenDetailComponent>;
        const route = ({ data: of({ kitchen: new Kitchen(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [KitchenDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(KitchenDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(KitchenDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.kitchen).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
