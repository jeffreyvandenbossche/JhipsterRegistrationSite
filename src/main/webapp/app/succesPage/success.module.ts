import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import { WeddingsiteSharedModule } from 'app/shared';
import { SuccessComponent } from './';
import { HOME_ROUTE } from 'app/succesPage/success.route';

@NgModule({
    imports: [WeddingsiteSharedModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [SuccessComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingsiteSuccessModule {
    constructor(private route: ActivatedRoute) {
        this.route.params.subscribe(params => console.log(params));
    }
}
