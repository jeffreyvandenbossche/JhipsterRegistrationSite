/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { WeddingsiteTestModule } from '../../../test.module';
import { PartyPartUpdateComponent } from 'app/entities/party-part/party-part-update.component';
import { PartyPartService } from 'app/entities/party-part/party-part.service';
import { PartyPart } from 'app/shared/model/party-part.model';

describe('Component Tests', () => {
    describe('PartyPart Management Update Component', () => {
        let comp: PartyPartUpdateComponent;
        let fixture: ComponentFixture<PartyPartUpdateComponent>;
        let service: PartyPartService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeddingsiteTestModule],
                declarations: [PartyPartUpdateComponent]
            })
                .overrideTemplate(PartyPartUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PartyPartUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyPartService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PartyPart(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.partyPart = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PartyPart();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.partyPart = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
