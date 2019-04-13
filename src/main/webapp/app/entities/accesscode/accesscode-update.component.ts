import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAccesscode } from 'app/shared/model/accesscode.model';
import { AccesscodeService } from './accesscode.service';
import { IPartyPart } from 'app/shared/model/party-part.model';
import { PartyPartService } from 'app/entities/party-part';

@Component({
    selector: 'jhi-accesscode-update',
    templateUrl: './accesscode-update.component.html'
})
export class AccesscodeUpdateComponent implements OnInit {
    accesscode: IAccesscode;
    isSaving: boolean;

    partyparts: IPartyPart[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private accesscodeService: AccesscodeService,
        private partyPartService: PartyPartService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ accesscode }) => {
            this.accesscode = accesscode;
        });
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
        if (this.accesscode.id !== undefined) {
            this.subscribeToSaveResponse(this.accesscodeService.update(this.accesscode));
        } else {
            this.subscribeToSaveResponse(this.accesscodeService.create(this.accesscode));
        }
    }

    generateNewCode() {
        this.accesscode.code = (
            Math.random()
                .toString(36)
                .substring(2, 5) +
            Math.random()
                .toString(36)
                .substring(2, 5)
        ).toUpperCase();
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAccesscode>>) {
        result.subscribe((res: HttpResponse<IAccesscode>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
