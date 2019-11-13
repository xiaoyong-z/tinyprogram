package com.market.history.mapper;

import com.market.history.History;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface HistoryMapper extends Mapper<History>, IdListMapper<History, Long>, InsertListMapper<History> {
}
