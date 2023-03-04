package com.example.search_app.capston.services;

import com.example.search_app.capston.dto.AddCompanyDto;
import com.example.search_app.capston.models.Company;

public interface CompanyService {
    void add(AddCompanyDto addCompanyDto);
     void updateCompany(AddCompanyDto addCompanyDto, long companyId);

     void deleteCompany(long companyId);
    Company getCompanyById(long companyId);


}
