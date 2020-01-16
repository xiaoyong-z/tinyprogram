package com.agent.activity.mapper;

import com.agent.activity.pojo.*;
import com.agent.activity.pojo.Route;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RouteMapper extends Mapper<Route> {
    @Select("select * from route")
    List<Route> getAllRoute();

    @Options(useGeneratedKeys = true, keyProperty = "route.route_id", keyColumn = "route_id")
    @Insert("insert into route(route_id, start_address, end_address, name)" +
            "values(null, #{route.start_address}, #{route.end_address}, #{route.name})")
    Integer insertRoute(@Param("route")  Route route);

    @Delete("delete from route where route_id = #{route_id}")
    void deleteRoute(@Param("route_id") Integer route_id);

    @Update("update route set start_address = #{route.start_address}" +
            ",end_address = #{route.end_address}" +
            ",name = #{route.name}" +
            "where route_id = #{route.route_id}")
    void updateRoute(@Param("route") Route route);

    @Select("select * from route_flight")
    List<RouteFlight> getAllRouteFlight();

    @Insert("insert into route_flight(route_id, flight_id, go_back)" +
            "values(#{route_id}, #{flight_id}, #{go_back})")
    Integer insertRouteFlight(@Param("route_id")  Integer route_id,
                              @Param("flight_id") String flight_id,
                              @Param("go_back") Boolean go_back);

    @Insert("delete from route_flight where route_id = #{route_id}")
    void deleteRouteFlight(@Param("route_id")  Integer route_id);

    @Insert("delete from route_flight where route_id = #{route_id} and go_back = #{go_back}")
    void deleteRouteFlight2(@Param("route_id")  Integer route_id,
                            @Param("go_back") Boolean go_back);



    @Select("select * from route_food")
    List<RouteFood> getAllRouteFood();

    @Update("update route_flight set flight_id = #{flight_id}" +
            "where route_id = #{route_id} and go_back = #{go_back}")
    void updateRouteFlight(@Param("route_id")  Integer route_id,
                           @Param("flight_id") String flight_id,
                           @Param("go_back") Boolean go_back);

    @Insert("insert into route_food(route_id, food_id)" +
            "values(#{route_id}, #{food_id})")
    Integer insertRouteFood(@Param("route_id")  Integer route_id,
                              @Param("food_id") Integer food_id);

    @Insert("delete from route_food where route_id = #{route_id}")
    void deleteRouteFood(@Param("route_id")  Integer route_id);

    @Insert("delete from route_food where route_id = #{route_id} and food_id = #{food_id}")
    void deleteRouteFood2(@Param("route_id")  Integer route_id,
                          @Param("food_id")   Integer food_id);
    
    @Select("select * from route_hotel")
    List<RouteHotel> getAllRouteHotel();

    @Insert("insert into route_hotel(route_id, hotel_id)" +
            "values(#{route_id}, #{hotel_id})")
    Integer insertRouteHotel(@Param("route_id")  Integer route_id,
                            @Param("hotel_id") Integer hotel_id);

    @Insert("delete from route_hotel where route_id = #{route_id}")
    void deleteRouteHotel(@Param("route_id")  Integer route_id);

    @Insert("delete from route_hotel where route_id = #{route_id} and hotel_id = #{hotel_id}")
    void deleteRouteHotel2(@Param("route_id")  Integer route_id,
                          @Param("hotel_id")   Integer hotel_id);
    
    @Select("select * from route_scenic")
    List<RouteScenic> getAllRouteScenic();

    @Insert("insert into route_scenic(route_id, scenic_id)" +
            "values(#{route_id}, #{scenic_id})")
    Integer insertRouteScenic(@Param("route_id")  Integer route_id,
                             @Param("scenic_id") Integer scenic_id);

    @Insert("delete from route_scenic where route_id = #{route_id}")
    void deleteRouteScenic(@Param("route_id")  Integer route_id);

    @Insert("delete from route_scenic where route_id = #{route_id} and scenic_id = #{scenic_id}")
    void deleteRouteScenic2(@Param("route_id")  Integer route_id,
                           @Param("scenic_id")   Integer scenic_id);
}
