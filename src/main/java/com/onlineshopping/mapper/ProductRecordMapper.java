package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.ProductRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRecordMapper {
    List<ProductRecord> selectProductRecords(ProductRecord productRecord, Integer startRow, Integer num);
    List<ProductRecord> selectProductRecordsByShopId(ProductRecord productRecord, Integer shopId,Integer startRow, Integer num);
    Integer countProductRecords(ProductRecord productRecord);
    Integer countProductRecordsByShopId(ProductRecord productRecord, Integer shopId);
    void insertProductRecord(ProductRecord productRecord);
    void updateProductRecordState(ProductRecord productRecord,Integer newState);
    void updateProductRecordById(ProductRecord productRecord);
}
