import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MembershipFormService } from './membership-form.service';
import { MembershipService } from '../service/membership.service';
import { IMembership } from '../membership.model';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

import { MembershipUpdateComponent } from './membership-update.component';

describe('Membership Management Update Component', () => {
  let comp: MembershipUpdateComponent;
  let fixture: ComponentFixture<MembershipUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let membershipFormService: MembershipFormService;
  let membershipService: MembershipService;
  let companyService: CompanyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MembershipUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MembershipUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MembershipUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    membershipFormService = TestBed.inject(MembershipFormService);
    membershipService = TestBed.inject(MembershipService);
    companyService = TestBed.inject(CompanyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Company query and add missing value', () => {
      const membership: IMembership = { id: 456 };
      const company: ICompany = { id: 84421 };
      membership.company = company;

      const companyCollection: ICompany[] = [{ id: 67694 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const additionalCompanies = [company];
      const expectedCollection: ICompany[] = [...additionalCompanies, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ membership });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(
        companyCollection,
        ...additionalCompanies.map(expect.objectContaining)
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const membership: IMembership = { id: 456 };
      const company: ICompany = { id: 86913 };
      membership.company = company;

      activatedRoute.data = of({ membership });
      comp.ngOnInit();

      expect(comp.companiesSharedCollection).toContain(company);
      expect(comp.membership).toEqual(membership);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMembership>>();
      const membership = { id: 123 };
      jest.spyOn(membershipFormService, 'getMembership').mockReturnValue(membership);
      jest.spyOn(membershipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membership });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: membership }));
      saveSubject.complete();

      // THEN
      expect(membershipFormService.getMembership).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(membershipService.update).toHaveBeenCalledWith(expect.objectContaining(membership));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMembership>>();
      const membership = { id: 123 };
      jest.spyOn(membershipFormService, 'getMembership').mockReturnValue({ id: null });
      jest.spyOn(membershipService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membership: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: membership }));
      saveSubject.complete();

      // THEN
      expect(membershipFormService.getMembership).toHaveBeenCalled();
      expect(membershipService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMembership>>();
      const membership = { id: 123 };
      jest.spyOn(membershipService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ membership });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(membershipService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCompany', () => {
      it('Should forward to companyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(companyService, 'compareCompany');
        comp.compareCompany(entity, entity2);
        expect(companyService.compareCompany).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
