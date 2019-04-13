import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyPart } from 'app/shared/model/party-part.model';

@Component({
    selector: 'jhi-party-part-detail',
    templateUrl: './party-part-detail.component.html'
})
export class PartyPartDetailComponent implements OnInit {
    partyPart: IPartyPart;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ partyPart }) => {
            this.partyPart = partyPart;
        });
    }

    previousState() {
        window.history.back();
    }
}
