/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CookerComponent } from 'app/entities/cooker/cooker.component';
import { CookerService } from 'app/entities/cooker/cooker.service';
import { Cooker } from 'app/shared/model/cooker.model';

describe('Component Tests', () => {
    describe('Cooker Management Component', () => {
        let comp: CookerComponent;
        let fixture: ComponentFixture<CookerComponent>;
        let service: CookerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [CookerComponent],
                providers: []
            })
                .overrideTemplate(CookerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CookerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CookerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Cooker(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.cookers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
