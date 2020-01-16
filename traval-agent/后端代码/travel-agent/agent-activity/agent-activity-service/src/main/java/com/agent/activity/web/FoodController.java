package com.agent.activity.web;

import com.agent.activity.pojo.Food;
import com.agent.activity.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("activity/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Food>> getAllFood(){
        return ResponseEntity.ok(foodService.getAllFood());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteFood(
            @RequestParam(value = "food_id") Integer food_id
    ){
        foodService.deleteFood(food_id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateTraveller(
            @RequestParam(value = "food_id") Integer food_id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description
    ){
        foodService.updateFood(food_id, name, description);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insertCompany(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "description") String description
    ){
        foodService.insertFood(name, description);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
