/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CookerDetailComponent } from 'app/entities/cooker/cooker-detail.component';
import { Cooker } from 'app/shared/model/cooker.model';

describe('Component Tests', () => {
    describe('Cooker Management Detail Component', () => {
        let comp: CookerDetailComponent;
        let fixture: ComponentFixture<CookerDetailComponent>;
        const route = ({ data: of({ cooker: new Cooker(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [CookerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CookerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CookerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cooker).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
