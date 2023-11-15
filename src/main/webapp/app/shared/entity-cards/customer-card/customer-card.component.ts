import {Component, Input} from '@angular/core';
import {ICustomer} from "../../../entities/customer/customer.model";
import {CustomerService, EntityResponseType} from "../../../entities/customer/service/customer.service";

@Component({
  selector: 'jhi-customer-card',
  templateUrl: './customer-card.component.html',
  styleUrls: ['./customer-card.component.scss']
})
export class CustomerCardComponent {
  @Input() customerId: string | null = null;
  customer: ICustomer | null = null;

  constructor(
    protected customerService: CustomerService,
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    if (this.customerId) {
      const id = parseInt(this.customerId);
      this.customerService.find(id).subscribe({
          next: (res: EntityResponseType) => {
            this.customer = res.body? res.body: null;
          }
      });
    }
  }

}
