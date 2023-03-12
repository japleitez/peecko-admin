import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompany } from '../company.model';

@Component({
  selector: 'jhi-company-detail',
  templateUrl: './company-detail.component.html',
})
export class CompanyDetailComponent implements OnInit {
  company: ICompany | null = null;
  companyId: number = 0;
  billing: string = 'BILLING';
  manager: string = 'MANAGER';
  operation: string = 'OPERATION';

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ company }) => {
      this.company = company;
      this.companyId = company.id;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
