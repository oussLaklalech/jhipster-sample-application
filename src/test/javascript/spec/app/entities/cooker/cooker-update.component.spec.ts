/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CookerUpdateComponent } from 'app/entities/cooker/cooker-update.component';
import { CookerService } from 'app/entities/cooker/cooker.service';
import { Cooker } from 'app/shared/model/cooker.model';

describe('Component Tests', () => {
    describe('Cooker Management Update Component', () => {
        let comp: CookerUpdateComponent;
        let fixture: ComponentFixture<CookerUpdateComponent>;
        let service: CookerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [CookerUpdateComponent]
            })
                .overrideTemplate(CookerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CookerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CookerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Cooker(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cooker = entity;
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
                    const entity = new Cooker();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cooker = entity;
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
