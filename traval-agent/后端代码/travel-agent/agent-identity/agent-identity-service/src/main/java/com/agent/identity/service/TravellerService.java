package com.agent.identity.service;


import com.agent.identity.mapper.TravellerMapper;
import com.agent.identity.pojo.Traveller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class TravellerService {
    @Autowired
    private TravellerMapper travellerMapper;
    public List<Traveller> getAllTraveller() { return travellerMapper.getAllTraveller(); }
    public void deleteTraveller(Integer traveller_id){
        travellerMapper.deleteTraveller(traveller_id);
    }
    public void updateTraveller(Integer traveller_id, String id_number, String name, String phone_number, String address, Integer age, Boolean gender, String email) {
        Traveller traveller = new Traveller();
        traveller.setTraveller_id(traveller_id);
        traveller.setId_number(id_number);
        traveller.setName(name);
        traveller.setPhone_number(phone_number);
        traveller.setAddress(address);
        traveller.setAge(age);
        traveller.setGender(gender);
        traveller.setEmail(email);
        travellerMapper.updateTraveller(traveller);
    }
    public void insertTraveller(String id_number, String name, String phone_number, String address, Integer age, Boolean gender, String email) {
        Traveller traveller = new Traveller();
        traveller.setTraveller_id(null);
        traveller.setId_number(id_number);
        traveller.setName(name);
        traveller.setPhone_number(phone_number);
        traveller.setAddress(address);
        traveller.setAge(age);
        traveller.setGender(gender);
        traveller.setEmail(email);
        travellerMapper.insertTraveller(traveller);
    }
}
