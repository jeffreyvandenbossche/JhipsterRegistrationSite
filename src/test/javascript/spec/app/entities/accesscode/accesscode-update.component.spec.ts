/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { WeddingsiteTestModule } from '../../../test.module';
import { AccesscodeUpdateComponent } from 'app/entities/accesscode/accesscode-update.component';
import { AccesscodeService } from 'app/entities/accesscode/accesscode.service';
import { Accesscode } from 'app/shared/model/accesscode.model';

describe('Component Tests', () => {
    describe('Accesscode Management Update Component', () => {
        let comp: AccesscodeUpdateComponent;
        let fixture: ComponentFixture<AccesscodeUpdateComponent>;
        let service: AccesscodeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeddingsiteTestModule],
                declarations: [AccesscodeUpdateComponent]
            })
                .overrideTemplate(AccesscodeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AccesscodeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccesscodeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Accesscode(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.accesscode = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Accesscode();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.accesscode = entity;
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
