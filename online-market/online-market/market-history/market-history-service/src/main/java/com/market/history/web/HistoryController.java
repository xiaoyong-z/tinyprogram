package com.market.history.web;

import com.market.history.History;
import com.market.history.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @RequestMapping(method = RequestMethod.GET)
    private ResponseEntity<List<History>> getUserHistory(
            @RequestParam(value="user_id") Long user_id
    ){
        return ResponseEntity.ok(historyService.getUserHistory(user_id));
    }

    @RequestMapping(method = RequestMethod.POST)
    private ResponseEntity<Void> addHistory(
            @RequestParam(value="user_id") Long user_id,
            @RequestParam(value="item_id") Long item_id,
            @RequestParam(value="count") Long count
    ){
        historyService.addHistory(user_id, item_id, count);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(value="batchAdd", method = RequestMethod.POST)
    private ResponseEntity<Void> addBatchHistory(
            @RequestParam(value="historyList") List<History> historyList
    ){
        historyService.addBatchHistory(historyList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
