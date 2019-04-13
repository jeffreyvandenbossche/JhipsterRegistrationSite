import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import { WeddingsiteSharedModule } from 'app/shared';
import { ReactiveFormsModule } from '@angular/forms';
import { HOME_ROUTE, RegisterformComponent } from './';

@NgModule({
    imports: [WeddingsiteSharedModule, ReactiveFormsModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [RegisterformComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingsiteRegisterModule {
    constructor(private route: ActivatedRoute) {
        this.route.params.subscribe(params => console.log(params));
    }
}
