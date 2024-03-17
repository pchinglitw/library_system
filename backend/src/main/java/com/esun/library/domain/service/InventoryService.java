package com.esun.library.domain.service;

import com.esun.library.domain.entity.Inventory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Inventory> findAllInventory() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("get_all_inventory", Inventory.class);
        query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
        query.execute();

        return query.getResultList();
    }

    @Transactional
    public Optional<Inventory> findInventoryById(Integer inventoryId) {
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("get_inventory_by_id", Inventory.class)
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR)
                .setParameter(1, inventoryId);
        query.execute();

        return query.getResultList().stream().findFirst();
    }

    @Transactional(rollbackFor = Exception.class)
    public void bookBorrow(List<Integer> inventoryIdList, Integer userId) {
        // update table Inventory.status
        entityManager.createStoredProcedureQuery("update_inventory_status")
                .registerStoredProcedureParameter(1, Integer[].class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                .setParameter(1, inventoryIdList.toArray(new Integer[0]))
                .setParameter(2, "2")
                .execute();

        // insert a new entity into table BookRecord
        entityManager.createStoredProcedureQuery("insert_book_record")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer[].class, ParameterMode.IN)
                .setParameter(1, userId)
                .setParameter(2, inventoryIdList.toArray(new Integer[0]))
                .execute();
    }

    @Transactional(rollbackFor = Exception.class)
    public void bookReturn(List<Integer> inventoryIdList, List<Integer> recordIdList) {
        // update table Inventory.status
        entityManager.createStoredProcedureQuery("update_inventory_status")
                .registerStoredProcedureParameter(1, Integer[].class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                .setParameter(1, inventoryIdList.toArray(new Integer[0]))
                .setParameter(2, "1")
                .execute();

        // update table BookRecord.returnTime
        entityManager.createStoredProcedureQuery("update_record")
                .registerStoredProcedureParameter(1, Integer[].class, ParameterMode.IN)
                .setParameter(1, recordIdList.toArray(new Integer[0]))
                .execute();
    }

}
