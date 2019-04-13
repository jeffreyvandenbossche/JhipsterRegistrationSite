import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from './person.service';
import { IFamily } from 'app/shared/model/family.model';
import { FamilyService } from 'app/entities/family';
import { IPartyPart } from 'app/shared/model/party-part.model';
import { PartyPartService } from 'app/entities/party-part';

@Component({
    selector: 'jhi-person-update',
    templateUrl: './person-update.component.html'
})
export class PersonUpdateComponent implements OnInit {
    person: IPerson;
    isSaving: boolean;

    families: IFamily[];

    partyparts: IPartyPart[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private personService: PersonService,
        private familyService: FamilyService,
        private partyPartService: PartyPartService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ person }) => {
            this.person = person;
        });
        this.familyService.query().subscribe(
            (res: HttpResponse<IFamily[]>) => {
                this.families = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.partyPartService.query().subscribe(
            (res: HttpResponse<IPartyPart[]>) => {
                this.partyparts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.person.id !== undefined) {
            this.subscribeToSaveResponse(this.personService.update(this.person));
        } else {
            this.subscribeToSaveResponse(this.personService.create(this.person));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPerson>>) {
        result.subscribe((res: HttpResponse<IPerson>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFamilyById(index: number, item: IFamily) {
        return item.id;
    }

    trackPartyPartById(index: number, item: IPartyPart) {
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
