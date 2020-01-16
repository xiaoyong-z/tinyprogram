package com.agent.activity.web;

import com.agent.activity.pojo.Activity;
import com.agent.activity.service.ActivityService;
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
@RequestMapping("activity/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Activity>> getAllActivity(){
        return ResponseEntity.ok(activityService.getAllActivity());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteActivity(
            @RequestParam(value = "activity_id") Integer activity_id
    ){
        activityService.deleteActivity(activity_id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateTraveller(
            @RequestParam(value = "activity_id") Integer activity_id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "adult_price") Double adult_price,
            @RequestParam(value = "child_price") Double child_price,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "route_id") Integer route_id,
            @RequestParam(value = "company_id") Integer company_id,
            @RequestParam(value = "days") Integer days,
            @RequestParam(value = "start_time") String raw_start_time,
            @RequestParam(value = "end_time") String raw_end_time
    ){
        Timestamp start_time = new Timestamp(Long.parseLong(raw_start_time));
        Timestamp end_time = new Timestamp(Long.parseLong(raw_end_time));
        activityService.updateActivity(activity_id, name, adult_price,
                child_price, description, route_id, company_id, days, start_time, end_time);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insertCompany(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "adult_price") Double adult_price,
            @RequestParam(value = "child_price") Double child_price,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "route_id") Integer route_id,
            @RequestParam(value = "company_id") Integer company_id,
            @RequestParam(value = "days") Integer days,
            @RequestParam(value = "start_time") String raw_start_time,
            @RequestParam(value = "end_time") String raw_end_time
    ){
        Timestamp start_time = new Timestamp(Long.parseLong(raw_start_time));
        Timestamp end_time = new Timestamp(Long.parseLong(raw_end_time));
        activityService.insertActivity(name, adult_price,
                child_price, description, route_id, company_id, days, start_time, end_time);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
