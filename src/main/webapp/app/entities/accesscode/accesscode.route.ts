import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Accesscode } from 'app/shared/model/accesscode.model';
import { AccesscodeService } from './accesscode.service';
import { AccesscodeComponent } from './accesscode.component';
import { AccesscodeDetailComponent } from './accesscode-detail.component';
import { AccesscodeUpdateComponent } from './accesscode-update.component';
import { AccesscodeDeletePopupComponent } from './accesscode-delete-dialog.component';
import { IAccesscode } from 'app/shared/model/accesscode.model';

@Injectable({ providedIn: 'root' })
export class AccesscodeResolve implements Resolve<IAccesscode> {
    constructor(private service: AccesscodeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Accesscode> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Accesscode>) => response.ok),
                map((accesscode: HttpResponse<Accesscode>) => accesscode.body)
            );
        }
        return of(new Accesscode());
    }
}

export const accesscodeRoute: Routes = [
    {
        path: 'accesscode',
        component: AccesscodeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingsiteApp.accesscode.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'accesscode/:id/view',
        component: AccesscodeDetailComponent,
        resolve: {
            accesscode: AccesscodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingsiteApp.accesscode.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'accesscode/new',
        component: AccesscodeUpdateComponent,
        resolve: {
            accesscode: AccesscodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingsiteApp.accesscode.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'accesscode/:id/edit',
        component: AccesscodeUpdateComponent,
        resolve: {
            accesscode: AccesscodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingsiteApp.accesscode.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const accesscodePopupRoute: Routes = [
    {
        path: 'accesscode/:id/delete',
        component: AccesscodeDeletePopupComponent,
        resolve: {
            accesscode: AccesscodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingsiteApp.accesscode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
