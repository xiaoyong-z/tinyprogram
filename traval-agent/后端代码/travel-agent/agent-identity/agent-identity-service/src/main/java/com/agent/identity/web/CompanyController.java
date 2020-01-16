package com.agent.identity.web;

import com.agent.identity.pojo.Company;
import com.agent.identity.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("identity/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Company>> getAllCompany(){
        return ResponseEntity.ok(companyService.getAllCompany());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCompany(
            @RequestParam(value = "company_id") Integer company_id
    ){
        companyService.deleteCompany(company_id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateTraveller(
            @RequestParam(value = "company_id") Integer company_id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "phone_number") String phone_number,
            @RequestParam(value = "address") String address
    ){
        companyService.updateCompany(company_id, name, phone_number, address);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insertCompany(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "phone_number") String phone_number,
            @RequestParam(value = "address") String address
    ){
        companyService.insertCompany(name, phone_number, address);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
