import { Route } from '@angular/router';

import { SuccessComponent } from 'app/succesPage/success.component';

export const HOME_ROUTE: Route = {
    path: 'success/:willAttend',
    component: SuccessComponent,
    data: {
        authorities: [],
        pageTitle: 'Bevestiging pagina'
    }
};
