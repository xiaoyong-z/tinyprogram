package com.agent.activity.service;


import com.agent.activity.mapper.ActivityMapper;
import com.agent.activity.pojo.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    public List<Activity> getAllActivity() {
        return activityMapper.getAllActivity();
    }

    public void deleteActivity(Integer activity_id) {
        activityMapper.deleteActivity(activity_id);
    }

    public void updateActivity(Integer activity_id, String name,
                             Double adult_price, Double child_price,
                             String description, Integer route_id,
                               Integer company_id, Integer days,
                               Timestamp start_time, Timestamp end_time) {
        Activity activity = new Activity();
        activity.setActivity_id(activity_id);
        activity.setName(name);
        activity.setAdult_price(adult_price);
        activity.setChild_price(child_price);
        activity.setDescription(description);
        activity.setRoute_id(route_id);
        activity.setCompany_id(company_id);
        activity.setDays(days);
        activity.setStart_time(start_time);
        activity.setEnd_time(end_time);
        activityMapper.updateActivity(activity);
    }

    public void insertActivity(String name, Double adult_price, Double child_price,
                               String description, Integer route_id, Integer company_id,
                               Integer days, Timestamp start_time, Timestamp end_time) {
        Activity activity = new Activity();
        activity.setActivity_id(null);
        activity.setName(name);
        activity.setAdult_price(adult_price);
        activity.setChild_price(child_price);
        activity.setDescription(description);
        activity.setRoute_id(route_id);
        activity.setCompany_id(company_id);
        activity.setDays(days);
        activity.setStart_time(start_time);
        activity.setEnd_time(end_time);
        activityMapper.insertActivity(activity);
    }
}
