package com.peecko.admin.service.dto;

import com.peecko.admin.domain.enumeration.ContactType;

public class CompanyBO {

    CompanyDTO company;

    ContactDTO manager;

    ContactDTO operation;

    ContactDTO billing;

    public CompanyBO() {
        this.manager = ContactDTO.createInstance(ContactType.MANAGER);
        this.operation = ContactDTO.createInstance(ContactType.OPERATION);
        this.billing = ContactDTO.createInstance(ContactType.BILLING);
    }

    public static CompanyBO createInstance(CompanyDTO company) {
        CompanyBO instance = new CompanyBO();
        instance.setCompany(company);
        return instance;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public ContactDTO getManager() {
        return manager;
    }

    public void setManager(ContactDTO manager) {
        this.manager = manager;
    }

    public ContactDTO getOperation() {
        return operation;
    }

    public void setOperation(ContactDTO operation) {
        this.operation = operation;
    }

    public ContactDTO getBilling() {
        return billing;
    }

    public void setBilling(ContactDTO billing) {
        this.billing = billing;
    }
}
