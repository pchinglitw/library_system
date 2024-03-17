package com.esun.library.app.mapper;

import com.esun.library.domain.entity.BorrowingRecord;
import com.esun.library.web.dto.response.RecordResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BorrowingRecordMapper {
    @Mapping(source = "recordId", target = "recordId")
    @Mapping(source = "inventoryId", target = "inventoryId")
    @Mapping(source = "borrowingTime", target = "borrowingTime")
    @Mapping(source = "returnTime", target = "returnTime")
    RecordResponse entityToInventoryResponse(BorrowingRecord entity);
}
