/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WeddingsiteTestModule } from '../../../test.module';
import { AccesscodeDetailComponent } from 'app/entities/accesscode/accesscode-detail.component';
import { Accesscode } from 'app/shared/model/accesscode.model';

describe('Component Tests', () => {
    describe('Accesscode Management Detail Component', () => {
        let comp: AccesscodeDetailComponent;
        let fixture: ComponentFixture<AccesscodeDetailComponent>;
        const route = ({ data: of({ accesscode: new Accesscode(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeddingsiteTestModule],
                declarations: [AccesscodeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AccesscodeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AccesscodeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.accesscode).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
