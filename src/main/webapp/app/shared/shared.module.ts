import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { HeroheaderComponent } from 'app/shared/heroheader/heroheader.component';
import { ReactiveFormsModule } from '@angular/forms';

import { HasAnyAuthorityDirective, JhiLoginModalComponent, WeddingsiteSharedCommonModule, WeddingsiteSharedLibsModule } from './';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { CountdowncalendarComponent } from 'app/shared/countdowncalendar/countdowncalendar.component';

@NgModule({
    imports: [WeddingsiteSharedLibsModule, WeddingsiteSharedCommonModule, ReactiveFormsModule, BrowserModule, HttpClientModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, HeroheaderComponent, CountdowncalendarComponent],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        WeddingsiteSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        HeroheaderComponent,
        CountdowncalendarComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingsiteSharedModule {}
