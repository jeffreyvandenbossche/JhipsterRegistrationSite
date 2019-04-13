import { Component, OnInit, Sanitizer, SecurityContext } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { SERVER_API_URL } from 'app/app.constants';

@Component({
    selector: 'jhi-heroheader',
    templateUrl: './heroheader.component.html',
    styleUrls: ['heroheader.scss']
})
export class HeroheaderComponent implements OnInit {
    title = 'app';
    error: any;
    exampleForm = new FormGroup({
        accessCode: new FormControl()
    });

    public resourceUrl = SERVER_API_URL;

    constructor(private formBuilder: FormBuilder, private http: HttpClient, private sanitizer: Sanitizer, private router: Router) {
        this.createForm();
    }

    ngOnInit() {}

    onSubmit() {
        const accesscode = this.sanitizer.sanitize(SecurityContext.URL, this.exampleForm.value.accessCode);

        this.http.get(SERVER_API_URL + 'api/accesscode/' + accesscode + '/family').subscribe(
            data => {
                console.log(data);
                this.router.navigate(['register', accesscode]);
            },
            err => {
                this.error = err;
                console.log(err);
            }
        );
        console.log(this.exampleForm.value.accessCode);
    }

    createForm() {
        this.exampleForm = this.formBuilder.group({
            accessCode: ''
        });
    }
}
