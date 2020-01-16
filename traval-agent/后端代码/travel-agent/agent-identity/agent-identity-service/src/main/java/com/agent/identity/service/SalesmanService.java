package com.agent.identity.service;

import com.agent.identity.mapper.SalesmanMapper;
import com.agent.identity.pojo.Salesman;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesmanService {
    @Autowired
    private SalesmanMapper salesmanMapper;

    public List<Salesman> getAllSalesman() {
        return salesmanMapper.getAllSalesman();
    }

    public void deleteSalesman(Integer salesman_id) {
        salesmanMapper.deleteSalesman(salesman_id);
    }

    public List<Object> loginSalesman(String account, String password) {
        List<Object> objects = new ArrayList<>();
        List<Salesman> listSalesman = getAllSalesman();
        for (Salesman salesman : listSalesman) {
            if(salesman.getAccount().equals(account)){
                if(salesman.getPassword().equals(password)){
                    objects.add(true);
                    objects.add(salesman);
                    return objects;
                }else{
                    objects.add(false);
                    return objects;
                }
            }
        }
        objects.add(false);
        return objects;
    }

    public void updateSalesman(Integer salesman_id, String id_number, String name, String phone_number,
                               String address, Integer age, Boolean gender,
                               String account, String password, Integer company_id, Boolean is_manager) {
        Salesman salesman = new Salesman();
        salesman.setSalesman_id(salesman_id);
        salesman.setId_number(id_number);
        salesman.setName(name);
        salesman.setPhone_number(phone_number);
        salesman.setAddress(address);
        salesman.setAge(age);
        salesman.setGender(gender);
        salesman.setAccount(account);
        salesman.setPassword(password);
        salesman.setCompany_id(company_id);
        salesmanMapper.updateSalesman(salesman);
    }

    public void insertSalesman(String id_number, String name, String phone_number,
                                String address, Integer age, Boolean gender,
                                String account, String password, Integer company_id, Boolean is_manager) {
        Salesman salesman = new Salesman();
        salesman.setSalesman_id(null);
        salesman.setId_number(id_number);
        salesman.setName(name);
        salesman.setPhone_number(phone_number);
        salesman.setAddress(address);
        salesman.setAge(age);
        salesman.setGender(gender);
        salesman.setAccount(account);
        salesman.setPassword(password);
        salesman.setCompany_id(company_id);
        salesman.setIs_manager(is_manager);
        salesmanMapper.insertSalesman(salesman);
    }
}
