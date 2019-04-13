import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeddingsiteSharedModule } from 'app/shared';
import {
    AccesscodeComponent,
    AccesscodeDetailComponent,
    AccesscodeUpdateComponent,
    AccesscodeDeletePopupComponent,
    AccesscodeDeleteDialogComponent,
    accesscodeRoute,
    accesscodePopupRoute
} from './';

const ENTITY_STATES = [...accesscodeRoute, ...accesscodePopupRoute];

@NgModule({
    imports: [WeddingsiteSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AccesscodeComponent,
        AccesscodeDetailComponent,
        AccesscodeUpdateComponent,
        AccesscodeDeleteDialogComponent,
        AccesscodeDeletePopupComponent
    ],
    entryComponents: [AccesscodeComponent, AccesscodeUpdateComponent, AccesscodeDeleteDialogComponent, AccesscodeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingsiteAccesscodeModule {}
