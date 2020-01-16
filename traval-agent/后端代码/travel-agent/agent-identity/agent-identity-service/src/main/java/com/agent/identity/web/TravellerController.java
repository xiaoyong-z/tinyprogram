package com.agent.identity.web;

import com.agent.identity.pojo.Traveller;
import com.agent.identity.service.TravellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("identity/traveller")
public class TravellerController {
    @Autowired
    private TravellerService travellerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Traveller>> getAllTraveller(){
        return ResponseEntity.ok(travellerService.getAllTraveller());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTraveller(
            @RequestParam(value = "traveller_id") Integer traveller_id
    ){
        travellerService.deleteTraveller(traveller_id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateTraveller(
            @RequestParam(value = "traveller_id") Integer traveller_id,
            @RequestParam(value = "id_number") String id_number,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "phone_number") String phone_number,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "age") Integer age,
            @RequestParam(value = "gender") Boolean gender,
            @RequestParam(value = "email") String email
    ){
        travellerService.updateTraveller(traveller_id, id_number, name, phone_number, address, age, gender, email);
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
        @RequestParam(value = "email") String email
    ){
        travellerService.insertTraveller(id_number, name, phone_number, address, age, gender, email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
