package com.agent.activity.mapper;

import com.agent.activity.pojo.Scenic;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ScenicMapper {
    @Select("select * from scenic")
    List<Scenic> getAllScenic();

    @Select("select * from scenic where scenic_id = #{scenic_id}")
    Scenic getSpecificScenic(Integer scenic_id);

    @Insert("insert into scenic(name, description)" +
            "values(#{scenic.name}, #{scenic.description})")
    void insertScenic(@Param("scenic")  Scenic scenic);

    @Delete("delete from scenic where scenic_id = #{scenic_id}")
    void deleteScenic(@Param("scenic_id") Integer scenic_id);

    @Update("update scenic set name = #{scenic.name}" +
            ",description= #{scenic.description}" +
            "where scenic_id = #{scenic.scenic_id}")
    void updateScenic(@Param("scenic") Scenic scenic);
}
