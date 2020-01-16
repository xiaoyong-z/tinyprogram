package com.agent.identity.service;

import com.agent.identity.mapper.CompanyMapper;
import com.agent.identity.pojo.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyMapper companyMapper;

    public List<Company> getAllCompany() {
        return companyMapper.getAllCompany();
    }

    public void deleteCompany(Integer company_id){
        companyMapper.deleteCompany(company_id);
    }

    public void updateCompany(Integer company_id, String name, String phone_number, String address) {
        Company company = new Company();
        company.setCompany_id(company_id);
        company.setName(name);
        company.setPhone_number(phone_number);
        company.setAddress(address);
        companyMapper.updateCompany(company);
    }

    public void insertCompany(String name, String phone_number, String address) {
        Company company = new Company();
        company.setCompany_id(null);
        company.setName(name);
        company.setPhone_number(phone_number);
        company.setAddress(address);
        companyMapper.insertCompany(company);
    }
}
