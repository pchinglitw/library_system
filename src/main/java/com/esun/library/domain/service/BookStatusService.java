package com.esun.library.domain.service;

import com.esun.library.domain.entity.BookStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookStatusService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<BookStatus> findAllStatusName() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("get_all_status_name", BookStatus.class);
        query.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
        query.execute();

        return query.getResultList();
    }
}
