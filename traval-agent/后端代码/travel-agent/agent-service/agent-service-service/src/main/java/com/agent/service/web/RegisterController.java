package com.agent.service.web;

import com.agent.service.pojo.Register;
import com.agent.service.pojo.RegisterDetail;
import com.agent.service.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("service/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Register>> getAllRegister(){
        return ResponseEntity.ok(registerService.getAllRegister());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RegisterDetail> insertCompany(
            @RequestParam(value = "traveller_id") Integer traveller_id,
            @RequestParam(value = "activity_id") Integer activity_id,
            @RequestParam(value = "register_date") String raw_register_date
    ){
        Timestamp register_date = new Timestamp(Long.parseLong(raw_register_date));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(registerService.insertRegister(traveller_id, activity_id, register_date));
    }
}
