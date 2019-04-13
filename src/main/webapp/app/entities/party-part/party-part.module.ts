import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeddingsiteSharedModule } from 'app/shared';
import {
    PartyPartComponent,
    PartyPartDetailComponent,
    PartyPartUpdateComponent,
    PartyPartDeletePopupComponent,
    PartyPartDeleteDialogComponent,
    partyPartRoute,
    partyPartPopupRoute
} from './';

const ENTITY_STATES = [...partyPartRoute, ...partyPartPopupRoute];

@NgModule({
    imports: [WeddingsiteSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PartyPartComponent,
        PartyPartDetailComponent,
        PartyPartUpdateComponent,
        PartyPartDeleteDialogComponent,
        PartyPartDeletePopupComponent
    ],
    entryComponents: [PartyPartComponent, PartyPartUpdateComponent, PartyPartDeleteDialogComponent, PartyPartDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingsitePartyPartModule {}
