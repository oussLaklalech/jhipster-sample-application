import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationConsumerModule } from './consumer/consumer.module';
import { JhipsterSampleApplicationCookerModule } from './cooker/cooker.module';
import { JhipsterSampleApplicationKitchenModule } from './kitchen/kitchen.module';
import { JhipsterSampleApplicationOrderPreparationModule } from './order-preparation/order-preparation.module';
import { JhipsterSampleApplicationStateModule } from './state/state.module';
import { JhipsterSampleApplicationDishModule } from './dish/dish.module';
import { JhipsterSampleApplicationCommentModule } from './comment/comment.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterSampleApplicationConsumerModule,
        JhipsterSampleApplicationCookerModule,
        JhipsterSampleApplicationKitchenModule,
        JhipsterSampleApplicationOrderPreparationModule,
        JhipsterSampleApplicationStateModule,
        JhipsterSampleApplicationDishModule,
        JhipsterSampleApplicationCommentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
