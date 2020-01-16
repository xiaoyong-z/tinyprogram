package com.agent.identity.web;

import com.agent.identity.pojo.Salesman;
import com.agent.identity.service.SalesmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("identity/salesman")
public class SalesmanController {
    @Autowired
    private SalesmanService salesmanService;

    @RequestMapping(value="login", method = RequestMethod.POST)
    public ResponseEntity<List<Object>> getAllSalesman(
            @RequestParam(value="account") String account,
            @RequestParam(value="password") String password
    ){
        return ResponseEntity.ok(salesmanService.loginSalesman(account, password));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Salesman>> getAllSalesman(){
        return ResponseEntity.ok(salesmanService.getAllSalesman());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSalesman(
            @RequestParam(value = "salesman_id") Integer salesman_id
    ){
        salesmanService.deleteSalesman(salesman_id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateSalesman(
            @RequestParam(value = "salesman_id") Integer salesman_id,
            @RequestParam(value = "id_number") String id_number,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "phone_number") String phone_number,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "age") Integer age,
            @RequestParam(value = "gender") Boolean gender,
            @RequestParam(value = "account") String account,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "company_id") Integer company_id,
            @RequestParam(value = "is_manager") Boolean is_manager
            ){
        salesmanService.updateSalesman(salesman_id, id_number, name,
                phone_number, address, age, gender, account,
                password, company_id, is_manager);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addTraveller(
            @RequestParam(value = "id_number") String id_number,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "phone_number") String phone_number,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "age") Integer age,
            @RequestParam(value = "gender") Boolean gender,
            @RequestParam(value = "account") String account,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "company_id") Integer company_id,
            @RequestParam(value = "is_manager") Boolean is_manager
    ){
        salesmanService .insertSalesman(id_number, name,
                phone_number, address, age, gender, account,
                password, company_id, is_manager);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
