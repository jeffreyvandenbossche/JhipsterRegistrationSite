/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WeddingsiteTestModule } from '../../../test.module';
import { PartyPartDetailComponent } from 'app/entities/party-part/party-part-detail.component';
import { PartyPart } from 'app/shared/model/party-part.model';

describe('Component Tests', () => {
    describe('PartyPart Management Detail Component', () => {
        let comp: PartyPartDetailComponent;
        let fixture: ComponentFixture<PartyPartDetailComponent>;
        const route = ({ data: of({ partyPart: new PartyPart(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeddingsiteTestModule],
                declarations: [PartyPartDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PartyPartDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PartyPartDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.partyPart).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
