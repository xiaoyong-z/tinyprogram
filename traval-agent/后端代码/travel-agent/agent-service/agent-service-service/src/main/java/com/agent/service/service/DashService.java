package com.agent.service.service;

import com.agent.service.mapper.DashMapper;
import com.agent.service.pojo.Dash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashService {
    @Autowired
    private DashMapper dashMapper;

    public Dash getDash() {
        return dashMapper.getDash();
    }
}
