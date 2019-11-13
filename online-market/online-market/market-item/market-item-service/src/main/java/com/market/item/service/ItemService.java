package com.market.item.service;


import com.market.common.enums.ExceptionEnum;
import com.market.common.exception.MkException;
import com.market.item.mapper.ItemMapper;
import com.market.item.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemMapper itemMapper;

    public List<Item> getALLItem() {
        List<Item> list = itemMapper.selectAll();
        if(CollectionUtils.isEmpty(list)){
            throw new MkException(ExceptionEnum.ITEM_NOT_FOUND);
        }
//        List<Item> return_list = new ArrayList<Item>();
//        for (Item item : list) {
//            if(item.getOn_sale()){
//            return_list.add(item);
//            }
//        }
        return list;
    }

    public void addNewItem(String name, String category, String img_url, Long price, Long belong_id, Long stock) throws MkException {
        Item item = new Item();
        item.setId(null);
        item.setName(name);
        item.setCategory(category);
        item.setImg_url(img_url);
        item.setPrice(price);
        item.setBelong_id(belong_id);
        item.setStock(stock);
        if(stock > 0) {
            item.setOn_sale(true);
        }
        int count = itemMapper.insert(item);
        if(count != 1){
            throw new MkException(ExceptionEnum.ITEM_ADD_ERROR);
        }
    }

    public void buyItem(Long id, Long buy_count) {
        Item item = new Item();
        item.setId(id);
        List<Item> list = itemMapper.select(item);
        if(CollectionUtils.isEmpty(list)){
            throw new MkException(ExceptionEnum.ITEM_BUY_ERROR);
        }
        Long stock_number = list.get(0).getStock();
        stock_number = stock_number - buy_count;
        if(stock_number < 0){
            throw new MkException(ExceptionEnum.ITEM_BUY_ERROR);
        }
        item = new Item();
        item.setId(id);
        item.setStock(stock_number);
        if(stock_number == 0){
            item.setOn_sale(false);
        }
        int count = itemMapper.updateByPrimaryKeySelective(item);
        if(count != 1){
            throw new MkException(ExceptionEnum.ITEM_BUY_ERROR);
        }
    }

    public void takeOffItem(Long id) {
        Item item = new Item();
        item.setId(id);
        List<Item> list = itemMapper.select(item);
        if(CollectionUtils.isEmpty(list)){
            throw new MkException(ExceptionEnum.ITEM_TAKEOFF_ERROR);
        }
        item = new Item();
        item.setId(id);
        item.setOn_sale(true);
        int count = itemMapper.updateByPrimaryKeySelective(item);
        if(count != 1){
            throw new MkException(ExceptionEnum.ITEM_TAKEOFF_ERROR);
        }
    }

    public Item getItem(Long id) {
        Item item = new Item();
        item.setId(id);
        List<Item> list = itemMapper.select(item);
        if(CollectionUtils.isEmpty(list)){
            throw new MkException(ExceptionEnum.SPEC_ITEM_NOT_FOUND);
        }
        return list.get(0);
    }

    public void addStock(Long id, Long addCount) {
        Item item = new Item();
        item.setId(id);
        List<Item> list = itemMapper.select(item);
        if(CollectionUtils.isEmpty(list)){
            throw new MkException(ExceptionEnum.ITEM_ADDSTOCK_ERROR);
        }
        Long stock_number = list.get(0).getStock();
        stock_number = stock_number + addCount;
        item = new Item();
        item.setId(id);
        item.setStock(stock_number);
        if (stock_number > 0){
            item.setOn_sale(true);
        }
        int count = itemMapper.updateByPrimaryKeySelective(item);
        if(count != 1){
            throw new MkException(ExceptionEnum.ITEM_ADDSTOCK_ERROR);
        }
    }
}
