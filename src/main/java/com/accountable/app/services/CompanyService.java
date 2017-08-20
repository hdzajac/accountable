package com.accountable.app.services;

import com.accountable.app.entities.Company;
import com.accountable.app.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("companyService")
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Set<Company> getCompaniesForUsername(String username){
        return companyRepository.findAllByUsername(username);
    }

    public Company getCompanyById(Integer id){
        return companyRepository.findById(id);
    }

    public void save (Company company){
        companyRepository.save(company);
    }
}
