package com.agent.activity.web;

import com.agent.activity.pojo.Hotel;
import com.agent.activity.pojo.Route;
import com.agent.activity.pojo.RouteAssemble;
import com.agent.activity.service.HotelService;
import com.agent.activity.service.RouteService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("activity/route")
public class RouteController {
    @Autowired
    private RouteService routeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RouteAssemble>> getAllRoute(){
        return ResponseEntity.ok(routeService.getAllRoute());
    }

    @RequestMapping(value = "route2", method = RequestMethod.GET)
    public ResponseEntity<List<Route>> getAllRoute2(){
        return ResponseEntity.ok(routeService.getAllRoute2());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insertRoute(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "start_address") String start_address,
            @RequestParam(value = "end_address") String end_address,
            @RequestParam(value = "start_flight_id") String start_flight_id,
            @RequestParam(value = "end_flight_id") String end_flight_id,
            @RequestParam(value = "foods") String raw_foods,
            @RequestParam(value = "hotels") String raw_hotels,
            @RequestParam(value = "scenics") String raw_scenics
    ){
        List<Integer> foods = JSONArray.parseArray(raw_foods, Integer.class);
        List<Integer> hotels = JSONArray.parseArray(raw_hotels,Integer.class);
        List<Integer> scenics = JSONArray.parseArray(raw_scenics,Integer.class);
        routeService.insertRoute(name, start_address, end_address, start_flight_id,
                                end_flight_id, foods, hotels, scenics);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteRoute(
            @RequestParam(value = "route_id") Integer route_id
    ){
        routeService.deleteRoute(route_id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateRoute(
            @RequestParam(value = "route_id") Integer route_id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "start_address") String start_address,
            @RequestParam(value = "end_address") String end_address,
            @RequestParam(value = "start_flight_id") String start_flight_id,
            @RequestParam(value = "end_flight_id") String end_flight_id,
            @RequestParam(value = "start_flight_change") Boolean start_flight_change,
            @RequestParam(value = "end_flight_change") Boolean end_flight_change,
            @RequestParam(value = "add_foods") String add_raw_foods,
            @RequestParam(value = "add_hotels") String add_raw_hotels,
            @RequestParam(value = "add_scenics") String add_raw_scenics,
            @RequestParam(value = "delete_foods") String delete_raw_foods,
            @RequestParam(value = "delete_hotels") String delete_raw_hotels,
            @RequestParam(value = "delete_scenics") String delete_raw_scenics
    ){
        List<Integer> add_foods = JSONArray.parseArray(add_raw_foods, Integer.class);
        List<Integer> add_hotels = JSONArray.parseArray(add_raw_hotels,Integer.class);
        List<Integer> add_scenics = JSONArray.parseArray(add_raw_scenics,Integer.class);
        List<Integer> delete_foods = JSONArray.parseArray(delete_raw_foods, Integer.class);
        List<Integer> delete_hotels = JSONArray.parseArray(delete_raw_hotels,Integer.class);
        List<Integer> delete_scenics = JSONArray.parseArray(delete_raw_scenics,Integer.class);
        routeService.updateRoute(route_id, name, start_address, end_address, start_flight_id,
                end_flight_id, start_flight_change, end_flight_change, add_foods, add_hotels,
                add_scenics, delete_foods, delete_hotels, delete_scenics);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
