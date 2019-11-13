package com.market.history.service;

import com.market.common.enums.ExceptionEnum;
import com.market.common.exception.MkException;
import com.market.history.History;
import com.market.history.mapper.HistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryMapper historyMapper;

    public List<History> getUserHistory(Long user_id) {
        History history = new History();
        history.setUser_id(user_id);
        List<History> list = historyMapper.select(history);
        if(CollectionUtils.isEmpty(list)){
            throw new MkException(ExceptionEnum.HISTORY_NOT_FOUND);
        }
        return list;
    }

    public void addHistory(Long user_id, Long item_id, Long buy_count) {
        History history = new History();
        history.setId(null);
        history.setUser_id(user_id);
        history.setItem_id(item_id);
        history.setCount(buy_count);
        int count = historyMapper.insert(history);
        if(count != 1){
            throw new MkException(ExceptionEnum.ADD_HISTORY_ERROR);
        }
    }

    public void addBatchHistory(List<History> historyList) {
        int count = historyMapper.insertList(historyList);
        if(count < 1){
            throw new MkException(ExceptionEnum.ADD_HISTORY_ERROR);
        }
    }
}
