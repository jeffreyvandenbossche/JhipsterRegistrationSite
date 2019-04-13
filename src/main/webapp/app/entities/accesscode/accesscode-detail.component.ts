import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccesscode } from 'app/shared/model/accesscode.model';

@Component({
    selector: 'jhi-accesscode-detail',
    templateUrl: './accesscode-detail.component.html'
})
export class AccesscodeDetailComponent implements OnInit {
    accesscode: IAccesscode;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ accesscode }) => {
            this.accesscode = accesscode;
        });
    }

    previousState() {
        window.history.back();
    }
}
