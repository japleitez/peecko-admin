import { Component, Input, OnInit } from '@angular/core';

import { IContact } from '../../contact/contact.model';
import { ContactService, EntityResponseType } from '../../contact/service/contact.service';

@Component({
  selector: 'address-detail',
  templateUrl: './address-detail.component.html',
})
export class AddressDetailComponent implements OnInit {
  @Input() companyId: number = 0;
  @Input() type: string = '';

  contact: IContact | null = null;

  constructor(protected contactService: ContactService) {}

  ngOnInit(): void {
    this.contactService.findByCompanyIdAndType(this.companyId, this.type).subscribe({
      next: (res: EntityResponseType) => {
        this.contact = res.body;
      },
    });
  }

  previousState(): void {
    window.history.back();
  }
}
