import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFamily } from 'app/shared/model/family.model';
import { FamilyService } from './family.service';
import { IAccesscode } from 'app/shared/model/accesscode.model';
import { AccesscodeService } from 'app/entities/accesscode';

@Component({
    selector: 'jhi-family-update',
    templateUrl: './family-update.component.html'
})
export class FamilyUpdateComponent implements OnInit {
    family: IFamily;
    isSaving: boolean;

    accesscodes: IAccesscode[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private familyService: FamilyService,
        private accesscodeService: AccesscodeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ family }) => {
            this.family = family;
        });
        this.accesscodeService.query({ filter: 'family-is-null' }).subscribe(
            (res: HttpResponse<IAccesscode[]>) => {
                if (!this.family.accesscode || !this.family.accesscode.id) {
                    this.accesscodes = res.body;
                } else {
                    this.accesscodeService.find(this.family.accesscode.id).subscribe(
                        (subRes: HttpResponse<IAccesscode>) => {
                            this.accesscodes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.family.id !== undefined) {
            this.subscribeToSaveResponse(this.familyService.update(this.family));
        } else {
            this.subscribeToSaveResponse(this.familyService.create(this.family));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFamily>>) {
        result.subscribe((res: HttpResponse<IFamily>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAccesscodeById(index: number, item: IAccesscode) {
        return item.id;
    }
}
