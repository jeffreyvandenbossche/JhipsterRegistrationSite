import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPartyPart } from 'app/shared/model/party-part.model';
import { PartyPartService } from './party-part.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';
import { IAccesscode } from 'app/shared/model/accesscode.model';
import { AccesscodeService } from 'app/entities/accesscode';

@Component({
    selector: 'jhi-party-part-update',
    templateUrl: './party-part-update.component.html'
})
export class PartyPartUpdateComponent implements OnInit {
    partyPart: IPartyPart;
    isSaving: boolean;

    people: IPerson[];

    accesscodes: IAccesscode[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private partyPartService: PartyPartService,
        private personService: PersonService,
        private accesscodeService: AccesscodeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ partyPart }) => {
            this.partyPart = partyPart;
        });
        this.personService.query().subscribe(
            (res: HttpResponse<IPerson[]>) => {
                this.people = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.accesscodeService.query().subscribe(
            (res: HttpResponse<IAccesscode[]>) => {
                this.accesscodes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.partyPart.id !== undefined) {
            this.subscribeToSaveResponse(this.partyPartService.update(this.partyPart));
        } else {
            this.subscribeToSaveResponse(this.partyPartService.create(this.partyPart));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPartyPart>>) {
        result.subscribe((res: HttpResponse<IPartyPart>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPersonById(index: number, item: IPerson) {
        return item.id;
    }

    trackAccesscodeById(index: number, item: IAccesscode) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
