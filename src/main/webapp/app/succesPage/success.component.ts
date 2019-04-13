import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-success',
    templateUrl: './success.component.html',
    styleUrls: ['success.scss']
})
export class SuccessComponent implements OnInit {
    constructor(public router: Router) {}

    ngOnInit(): void {}
}
