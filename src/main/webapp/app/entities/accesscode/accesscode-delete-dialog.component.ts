import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccesscode } from 'app/shared/model/accesscode.model';
import { AccesscodeService } from './accesscode.service';

@Component({
    selector: 'jhi-accesscode-delete-dialog',
    templateUrl: './accesscode-delete-dialog.component.html'
})
export class AccesscodeDeleteDialogComponent {
    accesscode: IAccesscode;

    constructor(private accesscodeService: AccesscodeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.accesscodeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'accesscodeListModification',
                content: 'Deleted an accesscode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-accesscode-delete-popup',
    template: ''
})
export class AccesscodeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ accesscode }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AccesscodeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.accesscode = accesscode;
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
