package com.agent.service.web;


import com.agent.service.pojo.Dash;
import com.agent.service.service.DashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("service/dash")
public class DashController {
    @Autowired
    private DashService dashService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Dash> getDash(){
        return ResponseEntity.ok(dashService.getDash());
    }

}
