import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyPart } from 'app/shared/model/party-part.model';
import { PartyPartService } from './party-part.service';

@Component({
    selector: 'jhi-party-part-delete-dialog',
    templateUrl: './party-part-delete-dialog.component.html'
})
export class PartyPartDeleteDialogComponent {
    partyPart: IPartyPart;

    constructor(private partyPartService: PartyPartService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.partyPartService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'partyPartListModification',
                content: 'Deleted an partyPart'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-party-part-delete-popup',
    template: ''
})
export class PartyPartDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ partyPart }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PartyPartDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.partyPart = partyPart;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
