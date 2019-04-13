import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WeddingsiteAccesscodeModule } from './accesscode/accesscode.module';
import { WeddingsiteFamilyModule } from './family/family.module';
import { WeddingsitePersonModule } from './person/person.module';
import { WeddingsitePartyPartModule } from './party-part/party-part.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        WeddingsiteAccesscodeModule,
        WeddingsiteFamilyModule,
        WeddingsitePersonModule,
        WeddingsitePartyPartModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingsiteEntityModule {}
