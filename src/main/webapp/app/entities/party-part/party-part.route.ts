import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PartyPart } from 'app/shared/model/party-part.model';
import { PartyPartService } from './party-part.service';
import { PartyPartComponent } from './party-part.component';
import { PartyPartDetailComponent } from './party-part-detail.component';
import { PartyPartUpdateComponent } from './party-part-update.component';
import { PartyPartDeletePopupComponent } from './party-part-delete-dialog.component';
import { IPartyPart } from 'app/shared/model/party-part.model';

@Injectable({ providedIn: 'root' })
export class PartyPartResolve implements Resolve<IPartyPart> {
    constructor(private service: PartyPartService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PartyPart> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PartyPart>) => response.ok),
                map((partyPart: HttpResponse<PartyPart>) => partyPart.body)
            );
        }
        return of(new PartyPart());
    }
}

export const partyPartRoute: Routes = [
    {
        path: 'party-part',
        component: PartyPartComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingsiteApp.partyPart.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'party-part/:id/view',
        component: PartyPartDetailComponent,
        resolve: {
            partyPart: PartyPartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingsiteApp.partyPart.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'party-part/new',
        component: PartyPartUpdateComponent,
        resolve: {
            partyPart: PartyPartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingsiteApp.partyPart.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'party-part/:id/edit',
        component: PartyPartUpdateComponent,
        resolve: {
            partyPart: PartyPartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingsiteApp.partyPart.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const partyPartPopupRoute: Routes = [
    {
        path: 'party-part/:id/delete',
        component: PartyPartDeletePopupComponent,
        resolve: {
            partyPart: PartyPartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingsiteApp.partyPart.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
