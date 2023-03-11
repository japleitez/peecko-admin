import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AddressDetailComponent } from './address-detail.component';

describe('Contact Management Detail Component', () => {
  let comp: AddressDetailComponent;
  let fixture: ComponentFixture<AddressDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddressDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ contact: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AddressDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AddressDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load contact on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.contact).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
