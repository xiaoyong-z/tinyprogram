package com.agent.activity.web;

import com.agent.activity.pojo.Scenic;
import com.agent.activity.service.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("activity/scenic")
public class ScenicController {
    @Autowired
    private ScenicService scenicService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Scenic>> getAllScenic(){
        return ResponseEntity.ok(scenicService.getAllScenic());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteScenic(
            @RequestParam(value = "scenic_id") Integer scenic_id
    ){
        scenicService.deleteScenic(scenic_id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateTraveller(
            @RequestParam(value = "scenic_id") Integer scenic_id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description
    ){
        scenicService.updateScenic(scenic_id, name, description);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insertCompany(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description
    ){
        scenicService.insertScenic(name, description);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
