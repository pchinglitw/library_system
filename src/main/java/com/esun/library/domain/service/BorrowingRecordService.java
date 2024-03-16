package com.esun.library.domain.service;

import com.esun.library.domain.entity.BorrowingRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BorrowingRecordService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Optional<BorrowingRecord> findRecordById(Integer recordId) {
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("get_record", BorrowingRecord.class)
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR)
                .setParameter(1, recordId);
        query.execute();

        return query.getResultList().stream().findFirst();
    }
}
