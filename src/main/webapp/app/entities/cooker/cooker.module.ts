import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    CookerComponent,
    CookerDetailComponent,
    CookerUpdateComponent,
    CookerDeletePopupComponent,
    CookerDeleteDialogComponent,
    cookerRoute,
    cookerPopupRoute
} from './';

const ENTITY_STATES = [...cookerRoute, ...cookerPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CookerComponent, CookerDetailComponent, CookerUpdateComponent, CookerDeleteDialogComponent, CookerDeletePopupComponent],
    entryComponents: [CookerComponent, CookerUpdateComponent, CookerDeleteDialogComponent, CookerDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationCookerModule {}
