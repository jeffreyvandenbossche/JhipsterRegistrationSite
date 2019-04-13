/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WeddingsiteTestModule } from '../../../test.module';
import { PartyPartDeleteDialogComponent } from 'app/entities/party-part/party-part-delete-dialog.component';
import { PartyPartService } from 'app/entities/party-part/party-part.service';

describe('Component Tests', () => {
    describe('PartyPart Management Delete Component', () => {
        let comp: PartyPartDeleteDialogComponent;
        let fixture: ComponentFixture<PartyPartDeleteDialogComponent>;
        let service: PartyPartService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeddingsiteTestModule],
                declarations: [PartyPartDeleteDialogComponent]
            })
                .overrideTemplate(PartyPartDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PartyPartDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyPartService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
