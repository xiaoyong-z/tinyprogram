package com.agent.service.service;

import com.agent.service.mapper.RegisterMapper;
import com.agent.service.pojo.ActivityDetail;
import com.agent.service.pojo.Register;
import com.agent.service.pojo.RegisterDetail;
import com.agent.service.pojo.TravellerDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RegisterService {
    @Autowired
    private RegisterMapper registerMapper;

    public List<Register> getAllRegister() {
        return registerMapper.getAllRegister();
    }

    public RegisterDetail insertRegister(Integer traveller_id,
                                         Integer activity_id,
                                         Timestamp register_date) {
        Register register = new Register();
        register.setTraveller_id(traveller_id);
        register.setActivity_id(activity_id);
        register.setRegister_date(register_date);
        registerMapper.insertRegister(register);
        TravellerDetail travellerDetail = registerMapper.getTravellerDetail(traveller_id);
        ActivityDetail activityDetail = registerMapper.getActivityDetail(activity_id);
        RegisterDetail registerDetail = new RegisterDetail();
        registerDetail.setTraveller_id_number(travellerDetail.getId_number());
        registerDetail.setTraveller_name(travellerDetail.getName());
        registerDetail.setGender(travellerDetail.getGender());
        registerDetail.setActivity_description(activityDetail.getDescription());
        registerDetail.setActivity_name(activityDetail.getName());
        return registerDetail;
    }
}
