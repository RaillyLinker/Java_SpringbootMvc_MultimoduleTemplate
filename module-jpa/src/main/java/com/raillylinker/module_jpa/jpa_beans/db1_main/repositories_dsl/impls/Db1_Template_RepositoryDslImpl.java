package com.raillylinker.module_jpa.jpa_beans.db1_main.repositories_dsl.impls;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.raillylinker.module_jpa.jpa_beans.db1_main.repositories_dsl.Db1_Template_RepositoryDsl;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public class Db1_Template_RepositoryDslImpl implements Db1_Template_RepositoryDsl {
    Db1_Template_RepositoryDslImpl(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            EntityManager entityManager
    ) {
        this.queryFactory = new JPAQueryFactory(entityManager);

    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    JPAQueryFactory queryFactory;
}
