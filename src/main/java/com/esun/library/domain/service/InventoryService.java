package com.esun.library.domain.service;

import com.esun.library.domain.entity.Book;
import com.esun.library.domain.entity.Inventory;
import com.esun.library.domain.repository.InventoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
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
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("getInventoryById", Inventory.class)
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR)
                .setParameter(1, inventoryId);
        query.execute();

        return query.getResultList().stream().findFirst();
    }

    @Transactional(rollbackFor = Exception.class)
    public void bookBorrow(Integer inventoryId, Integer userId) {
        // update table Inventory.status
        entityManager.createStoredProcedureQuery("update_inventory_status")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                .setParameter(1, inventoryId)
                .setParameter(2, "2")
                .execute();

        // insert a new entity into table BookRecord
        entityManager.createStoredProcedureQuery("insert_book_record")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                .setParameter(1, userId)
                .setParameter(2, inventoryId)
                .execute();
    }

    @Transactional(rollbackFor = Exception.class)
    public void bookReturn(Integer inventoryId, Integer recordId) {
        // update table Inventory.status
        entityManager.createStoredProcedureQuery("update_inventory_status")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                .setParameter(1, inventoryId)
                .setParameter(2, "1")
                .execute();

        // update table BookRecord.returnTime
        entityManager.createStoredProcedureQuery("update_record")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .setParameter(1, recordId)
                .execute();
    }

}
