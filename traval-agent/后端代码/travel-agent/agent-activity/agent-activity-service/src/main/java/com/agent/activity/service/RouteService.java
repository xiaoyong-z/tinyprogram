package com.agent.activity.service;

import com.agent.activity.mapper.*;
import com.agent.activity.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {
    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private ScenicMapper scenicMapper;

    public List<RouteAssemble> getAllRoute() {
        List<Route> listRoutes = routeMapper.getAllRoute();
        List<RouteFlight> listRouteFlights = routeMapper.getAllRouteFlight();
        List<RouteFood> listRouteFoods = routeMapper.getAllRouteFood();
        List<RouteHotel> listRouteHotels = routeMapper.getAllRouteHotel();
        List<RouteScenic> listRouteScenics = routeMapper.getAllRouteScenic();
        List<RouteAssemble> listRouteAssembles = new ArrayList<>();
        for (Route route : listRoutes) {
            RouteAssemble routeAssemble = new RouteAssemble();
            routeAssemble.setBase_route(route);
            List<Food> foods = new ArrayList<>();
            List<Flight> flights = new ArrayList<>();
            List<Hotel> hotels = new ArrayList<>();
            List<Scenic> scenics = new ArrayList<>();
            for (RouteFood routeFood : listRouteFoods) {
                if(routeFood.getRoute_id().equals(route.getRoute_id())){
                    foods.add(foodMapper.getSpecificFood(routeFood.getFood_id()));
                }
            }
            for (RouteFlight routeFlight : listRouteFlights) {
                if(routeFlight.getRoute_id().equals(route.getRoute_id())){
                    flights.add(flightMapper.getSpecificFlight(routeFlight.getFlight_id()));
                    if(routeFlight.getGo_back().equals(true)){
                        routeAssemble.setStart_flight_id(routeFlight.getFlight_id());
                    }else{
                        routeAssemble.setEnd_flight_id(routeFlight.getFlight_id());
                    }
                }
            }
            for (RouteHotel routeHotel : listRouteHotels) {
                if(routeHotel.getRoute_id().equals(route.getRoute_id())){
                    hotels.add(hotelMapper.getSpecificHotel(routeHotel.getHotel_id()));
                }
            }
            for (RouteScenic routeScenic : listRouteScenics) {
                if(routeScenic.getRoute_id().equals(route.getRoute_id())){
                    scenics.add(scenicMapper.getSpecificScenic(routeScenic.getScenic_id()));
                }
            }
            routeAssemble.setRoute_food(foods);
            routeAssemble.setRoute_flight(flights);
            routeAssemble.setRoute_hotel(hotels);
            routeAssemble.setRoute_scenic(scenics);
            listRouteAssembles.add(routeAssemble);
        }
        return listRouteAssembles;
    }


    public void updateRoute(Integer route_id,
                            String name,
                            String start_address,
                            String end_address,
                            String start_flight_id,
                            String end_flight_id,
                            Boolean start_flight_change,
                            Boolean end_flight_change,
                            List<Integer> add_foods,
                            List<Integer> add_hotels,
                            List<Integer> add_scenics,
                            List<Integer> delete_foods,
                            List<Integer> delete_hotels,
                            List<Integer> delete_scenics
                            ){
        Route route = new Route();
        route.setRoute_id(route_id);
        route.setName(name);
        route.setStart_address(start_address);
        route.setEnd_address(end_address);
        routeMapper.updateRoute(route);
        if(start_flight_change){
            routeMapper.deleteRouteFlight2(route_id, true);
            routeMapper.insertRouteFlight(route_id, start_flight_id, true);
        }
        if(end_flight_change){
            routeMapper.deleteRouteFlight2(route_id, false);
            routeMapper.insertRouteFlight(route_id, end_flight_id, false);
        }
        for (Integer food_id : delete_foods) {
            routeMapper.deleteRouteFood2(route_id, food_id);
        }
        for (Integer hotel_id : delete_hotels) {
            routeMapper.deleteRouteHotel2(route_id, hotel_id);
        }
        for (Integer scenic_id : delete_scenics) {
            routeMapper.deleteRouteScenic2(route_id, scenic_id);
        }
        for (Integer food_id : add_foods) {
            routeMapper.insertRouteFood(route_id, food_id);
        }
        for (Integer hotel_id : add_hotels) {
            routeMapper.insertRouteHotel(route_id, hotel_id);
        }
        for (Integer scenic_id : add_scenics) {
            routeMapper.insertRouteScenic(route_id, scenic_id);
        }
    }


    public void insertRoute(String name,
                            String start_address,
                            String end_address,
                            String start_flight_id,
                            String end_flight_id,
                            List<Integer> foods,
                            List<Integer> hotels,
                            List<Integer> scenics){
        Route route = new Route();
        route.setRoute_id(null);
        route.setName(name);
        route.setStart_address(start_address);
        route.setEnd_address(end_address);
        routeMapper.insertRoute(route);
        routeMapper.insertRouteFlight(route.getRoute_id(), start_flight_id, true);
        routeMapper.insertRouteFlight(route.getRoute_id(), end_flight_id, false);
        for (Integer food_id : foods) {
            routeMapper.insertRouteFood(route.getRoute_id(), food_id);
        }
        for (Integer hotel_id : hotels) {
            routeMapper.insertRouteHotel(route.getRoute_id(), hotel_id);
        }
        for (Integer scenic_id : scenics) {
            routeMapper.insertRouteScenic(route.getRoute_id(), scenic_id);
        }
    }

    public void deleteRoute(Integer route_id) {
        routeMapper.deleteRouteFlight(route_id);
        routeMapper.deleteRouteFood(route_id);
        routeMapper.deleteRouteHotel(route_id);
        routeMapper.deleteRouteScenic(route_id);
        routeMapper.deleteRoute(route_id);
    }

    public List<Route> getAllRoute2() {
        return routeMapper.getAllRoute();
    }
}
