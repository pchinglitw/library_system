package com.esun.library.domain.service;

import com.esun.library.domain.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Optional<User> findByPhoneNum(String phoneNum) {
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery("get_user_by_phone_number", User.class)
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR)
                .setParameter(1, phoneNum);
        query.execute();

        return query.getResultList().stream().findFirst();
    }

    @Transactional
    public void register(User user) {
        // insert a new entity into table User
        entityManager.createStoredProcedureQuery("insert_new_user")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                .setParameter(1, user.getPhoneNumber())
                .setParameter(2, user.getPasswordHash())
                .setParameter(3, user.getUserName())
                .execute();
    }
}
