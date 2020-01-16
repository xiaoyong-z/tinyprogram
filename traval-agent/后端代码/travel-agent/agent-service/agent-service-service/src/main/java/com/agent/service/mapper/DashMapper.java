package com.agent.service.mapper;

import com.agent.service.pojo.Bill;
import com.agent.service.pojo.Dash;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DashMapper {
    @Select("call NewProc()")
    Dash getDash();
}
