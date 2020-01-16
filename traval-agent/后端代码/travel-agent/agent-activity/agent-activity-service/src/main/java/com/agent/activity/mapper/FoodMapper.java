package com.agent.activity.mapper;

import com.agent.activity.pojo.Food;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FoodMapper extends Mapper<Food> {
    @Select("select * from food")
    List<Food> getAllFood();

    @Select("select * from food where food_id = #{food_id}")
    Food getSpecificFood(Integer food_id);

    @Insert("insert into food(name, description)" +
            "values(#{food.name}, #{food.description})")
    void insertFood(@Param("food")  Food food);

    @Delete("delete from food where food_id = #{food_id}")
    void deleteFood(@Param("food_id") Integer food_id);

    @Update("update food set name = #{food.name}" +
            ",description= #{food.description}" +
            "where food_id = #{food.food_id}")
    void updateFood(@Param("food") Food food);
}
