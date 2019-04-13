import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPartyPart } from 'app/shared/model/party-part.model';
import { Principal } from 'app/core';
import { PartyPartService } from './party-part.service';

@Component({
    selector: 'jhi-party-part',
    templateUrl: './party-part.component.html'
})
export class PartyPartComponent implements OnInit, OnDestroy {
    partyParts: IPartyPart[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private partyPartService: PartyPartService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.partyPartService.query().subscribe(
            (res: HttpResponse<IPartyPart[]>) => {
                this.partyParts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPartyParts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPartyPart) {
        return item.id;
    }

    registerChangeInPartyParts() {
        this.eventSubscriber = this.eventManager.subscribe('partyPartListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
