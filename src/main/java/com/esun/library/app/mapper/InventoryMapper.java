package com.esun.library.app.mapper;

import com.esun.library.domain.entity.Inventory;
import com.esun.library.web.dto.response.InventoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    @Mapping(source = "inventoryId", target = "inventoryId")
    @Mapping(source = "storeTime", target = "storeTime")
    @Mapping(source = "isbn", target = "isbn")
    @Mapping(target = "status", ignore = true)
    List<InventoryResponse> entityToInventoryResponse(List<Inventory> entityList);

    @Mapping(source = "inventoryId", target = "inventoryId")
    @Mapping(source = "storeTime", target = "storeTime")
    @Mapping(source = "isbn", target = "isbn")
    @Mapping(target = "status", ignore = true)
    InventoryResponse entityToInventoryResponse(Inventory entityList);

}
