/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeddingsiteTestModule } from '../../../test.module';
import { PartyPartComponent } from 'app/entities/party-part/party-part.component';
import { PartyPartService } from 'app/entities/party-part/party-part.service';
import { PartyPart } from 'app/shared/model/party-part.model';

describe('Component Tests', () => {
    describe('PartyPart Management Component', () => {
        let comp: PartyPartComponent;
        let fixture: ComponentFixture<PartyPartComponent>;
        let service: PartyPartService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeddingsiteTestModule],
                declarations: [PartyPartComponent],
                providers: []
            })
                .overrideTemplate(PartyPartComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PartyPartComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyPartService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PartyPart(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.partyParts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
