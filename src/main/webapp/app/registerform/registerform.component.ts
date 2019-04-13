import { Component, OnInit, Sanitizer } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Person } from 'app/shared/model/person.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Family, IFamily } from 'app/shared/model/family.model';
import { IPartyPart } from 'app/shared/model/party-part.model';
import { Accesscode } from 'app/shared/model/accesscode.model';
import { SERVER_API_URL } from 'app/app.constants';

@Component({
    selector: 'jhi-register-form',
    templateUrl: './registerform.component.html',
    styleUrls: ['registerform.scss']
})
export class RegisterformComponent implements OnInit {
    title = 'app';
    family: Family[];
    Family: IFamily;
    registerForm = new FormGroup({
        willAttend: new FormControl()
    });
    public id: string;
    PartyParts: IPartyPart[];
    public personArray: Person[];
    public accessCodeInfo: Accesscode;
    public willAttend: boolean;
    public resourceUrl = SERVER_API_URL;

    constructor(
        private formBuilder: FormBuilder,
        private http: HttpClient,
        private sanitizer: Sanitizer,
        private route: ActivatedRoute,
        private router: Router
    ) {
        // this.createForm();
    }

    ngOnInit() {
        this.route.params.subscribe(paramsId => {
            this.id = paramsId.accesscode;
        });
        this.getFamilyInfo();
        this.createForm();
    }

    get persons() {
        return this.registerForm.get('persons') as FormArray;
    }

    onSubmit() {
        // console.log(this.registerForm.value);

        this.accessCodeInfo.willAttend = Boolean(this.registerForm.value.willAttend);
        // console.log(SERVER_API_URL);
        // this.http.put('api/accesscodes/form', this.accessCodeInfo).subscribe();
        this.http.post(SERVER_API_URL + 'api/form/' + this.id, this.registerForm.value).subscribe(
            (data: any[]) => {
                // console.log(data);
                this.router.navigate(['success', this.registerForm.value.willAttend]);
            },
            err => {
                console.log(err);
            }
        );
    }

    getFamilyInfo() {
        this.http.get(SERVER_API_URL + 'api/accesscode/' + this.id + '/info').subscribe(
            (data: any[]) => {
                this.personArray = data[1];
                this.accessCodeInfo = data[0];
                if (this.personArray[0] != null) {
                    this.Family = this.personArray[0].family;
                }
                this.willAttend = this.accessCodeInfo.willAttend;
                this.registerForm.patchValue({ willAttend: String(this.willAttend) });
                this.PartyParts = this.accessCodeInfo.partyParts;
                // this.registerForm
                for (const result of this.personArray) {
                    // this.personArray.push(result);
                    this.persons.push(
                        this.formBuilder.group({
                            id: result.id,
                            firstName: [result.firstName],
                            familyName: [result.familyName],
                            foodRestrictions: result.foodRestriction,
                            // partyParts: this.formBuilder.control(result.partyParts)
                            partyParts: this.initPartyPartFilledIn(this.PartyParts, result.partyParts)
                        })
                    );
                }
            },
            err => {
                console.log('Error occured');
            }
        );
    }

    removePerson(id: any) {
        this.persons.removeAt(id);
    }

    addNewPerson() {
        const control = <FormArray>this.registerForm.get('persons');
        control.push(this.initPerson());
    }

    private createForm() {
        this.registerForm = new FormGroup({
            willAttend: new FormControl(this.willAttend),
            persons: new FormArray([])
        });
    }

    getPartyParts(form) {
        return form.controls.partyParts.controls;
    }

    private initPerson() {
        return new FormGroup({
            id: new FormControl(''),
            firstName: new FormControl(''),
            familyName: new FormControl(''),
            foodRestrictions: new FormControl(''),
            partyParts: this.initPartyPartFilledIn(this.PartyParts, null)
        });
    }

    getPersons(registerForm) {
        return registerForm.controls.persons.controls;
    }

    private initPartyPartFilledIn(PartyParts: IPartyPart[], partyPartsChecked: IPartyPart[]) {
        const partyPartArray = new FormArray([]);
        PartyParts.forEach(partyPart => {
            let checked = false;
            if (partyPartsChecked != null) {
                partyPartsChecked.forEach(partyPartChecked => {
                    if (partyPartChecked.id === partyPart.id) {
                        checked = true;
                    }
                });
            }

            const partyPartFormGroup = new FormGroup({
                id: new FormControl(partyPart.id),
                name: new FormControl(partyPart.partName),
                checked: new FormControl(checked)
            });
            partyPartArray.push(partyPartFormGroup);
        });
        return partyPartArray;
    }
}
