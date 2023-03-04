package com.example.search_app.capston.services.impl;

import com.example.search_app.capston.dto.AddCompanyDto;
import com.example.search_app.capston.models.Company;
import com.example.search_app.capston.repositories.CompanyRepository;
import com.example.search_app.capston.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public void add(AddCompanyDto addCompanyDto) {
        // checking, company is already present with the company name
        companyRepository.findByNameEqualsIgnoreCase(addCompanyDto.getName())
                .ifPresent(company -> {throw new RuntimeException("Company is already present with name:"+ addCompanyDto.getName());});

        // creating company object
        Company company = new Company();
        company.setName(addCompanyDto.getName());
        company.setCompanyAddress(addCompanyDto.getCompanyAddress());
        company.setPhone(addCompanyDto.getPhone());
        company.setEmail(addCompanyDto.getEmail());

        companyRepository.save(company);

    }

    @Override
    public void updateCompany(AddCompanyDto addCompanyDto, long companyId) {
        Company company = getCompanyById(companyId);
        if (company.getName().equalsIgnoreCase(addCompanyDto.getName())){
            throw new RuntimeException("Company is already present with name:"+ addCompanyDto.getName());
        }
        company.setName(addCompanyDto.getName());
        company.setCompanyAddress(addCompanyDto.getCompanyAddress());
        company.setPhone(addCompanyDto.getPhone());
        company.setEmail(addCompanyDto.getEmail());
        companyRepository.save(company);
    }

    @Override
    public void deleteCompany(long companyId) {
        Company company = getCompanyById(companyId);
        // checking if any jobs is present with this company
        if (company.getJob().size() != 0) {
            throw new RuntimeException("Failed to delete Company, Some Jobs are associated with this company.");
        }
        companyRepository.delete(company);
    }

    @Override
    public Company getCompanyById(long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with id:"+ companyId));
    }
}
