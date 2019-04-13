import { Route } from '@angular/router';

import { RegisterformComponent } from './';

export const HOME_ROUTE: Route = {
    path: 'register/:accesscode',
    component: RegisterformComponent,
    data: {
        authorities: [],
        pageTitle: 'home.title'
    }
};
