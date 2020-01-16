package com.agent.activity.service;

import com.agent.activity.mapper.ScenicMapper;
import com.agent.activity.pojo.Scenic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenicService {
    @Autowired
    private ScenicMapper scenicMapper;

    public List<Scenic> getAllScenic() {
        return scenicMapper.getAllScenic();
    }

    public void deleteScenic(Integer scenic_id) {
        scenicMapper.deleteScenic(scenic_id);
    }

    public void updateScenic(Integer scenic_id, String name,
                           String description) {
        Scenic scenic = new Scenic();
        scenic.setScenic_id(scenic_id);
        scenic.setName(name);
        scenic.setDescription(description);
        scenicMapper.updateScenic(scenic);
    }

    public void insertScenic(String name,
                           String description) {
        Scenic scenic = new Scenic();
        scenic.setScenic_id(null);
        scenic.setName(name);
        scenic.setDescription(description);
        scenicMapper.insertScenic(scenic);
    }
}
