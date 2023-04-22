package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.ShopRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRecordMapper {
    List<ShopRecord> selectShopRecords(ShopRecord shopRecord, Integer startRow, Integer num);
    Integer countShopRecords(ShopRecord shopRecord);
    void insertShopRecord(ShopRecord shopRecord);
    void updateShopRecordState(ShopRecord shopRecord,Integer newState);
    void updateShopById(ShopRecord shopRecord);
}
