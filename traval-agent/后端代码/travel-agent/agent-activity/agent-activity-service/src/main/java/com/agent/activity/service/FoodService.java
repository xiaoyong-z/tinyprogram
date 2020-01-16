package com.agent.activity.service;

import com.agent.activity.mapper.FoodMapper;
import com.agent.activity.pojo.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    @Autowired
    private FoodMapper foodMapper;

    public List<Food> getAllFood() {
        return foodMapper.getAllFood();
    }

    public void deleteFood(Integer food_id) {
        foodMapper.deleteFood(food_id);
    }

    public void updateFood(Integer food_id, String name,
                            String description) {
        Food food = new Food();
        food.setFood_id(food_id);
        food.setName(name);
        food.setDescription(description);
        foodMapper.updateFood(food);
    }

    public void insertFood(String name,
                            String description) {
        Food food = new Food();
        food.setFood_id(null);
        food.setName(name);
        food.setDescription(description);
        foodMapper.insertFood(food);
    }
}
