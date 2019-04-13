import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage } from 'ngx-webstorage';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { WeddingsiteSharedModule } from 'app/shared';
import { WeddingsiteCoreModule } from 'app/core';
import { WeddingsiteAppRoutingModule } from './app-routing.module';
import { WeddingsiteHomeModule } from 'app/home';
import { WeddingsiteRegisterModule } from 'app/registerform';
import { WeddingsiteAccountModule } from './account/account.module';
import { WeddingsiteEntityModule } from './entities/entity.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import * as moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { ActiveMenuDirective, ErrorComponent, FooterComponent, JhiMainComponent, NavbarComponent, PageRibbonComponent } from './layouts';
import { WeddingsiteSuccessModule } from 'app/succesPage';

@NgModule({
    imports: [
        BrowserModule,
        WeddingsiteAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        WeddingsiteSharedModule,
        WeddingsiteCoreModule,
        WeddingsiteHomeModule,
        WeddingsiteRegisterModule,
        WeddingsiteAccountModule,
        WeddingsiteSuccessModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
        WeddingsiteEntityModule,
        FontAwesomeModule
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class WeddingsiteAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
