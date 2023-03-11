import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContact } from '../../contact/contact.model';

@Component({
  selector: 'address-detail',
  templateUrl: './address-detail.component.html',
})
export class AddressDetailComponent implements OnInit {
  @Input() companyId = '';
  @Input() contactType = '';
  contact: IContact | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contact }) => {
      this.contact = contact;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
