package com.raillylinker.module_jpa.jpa_beans.db1_main.repositories_dsl.impls;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_Template_FkTestManyToOneChild;
import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_Template_FkTestParent;
import com.raillylinker.module_jpa.jpa_beans.db1_main.repositories_dsl.Db1_Template_RepositoryDsl;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.raillylinker.module_jpa.jpa_beans.db1_main.entities.QDb1_Template_FkTestManyToOneChild.db1_Template_FkTestManyToOneChild;
import static com.raillylinker.module_jpa.jpa_beans.db1_main.entities.QDb1_Template_FkTestParent.db1_Template_FkTestParent;

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


    ////
    @Override
    @NotNull
    @org.jetbrains.annotations.NotNull
    public List<@Valid @NotNull Db1_Template_FkTestParent> findParentWithChildren() {
        return queryFactory
                .selectFrom(db1_Template_FkTestParent)
                .leftJoin(db1_Template_FkTestParent.fkTestManyToOneChildList, db1_Template_FkTestManyToOneChild)
                .fetchJoin() // fetchJoin을 사용하여 자식 엔티티를 함께 가져옴
                .fetch(); // 결과를 가져옴
    }


    ////
    @Override
    @NotNull
    @org.jetbrains.annotations.NotNull
    public List<@Valid @NotNull Db1_Template_FkTestParent> findParentWithChildrenByName(@org.jetbrains.annotations.NotNull String parentName) {
        return queryFactory
                .selectFrom(db1_Template_FkTestParent)
                .leftJoin(db1_Template_FkTestParent.fkTestManyToOneChildList, db1_Template_FkTestManyToOneChild)
                .fetchJoin()
                .where(db1_Template_FkTestParent.parentName.like("%" + parentName.replace(" ", "") + "%"))
                .fetch();
    }


    ////
    @Override
    @NotNull
    @org.jetbrains.annotations.NotNull
    public List<Db1_Template_FkTestManyToOneChild> findChildByParentId(@org.jetbrains.annotations.NotNull Long parentId) {
        return queryFactory
                .selectFrom(db1_Template_FkTestManyToOneChild)
                .leftJoin(db1_Template_FkTestManyToOneChild.fkTestParent, db1_Template_FkTestParent)
                .where(db1_Template_FkTestParent.uid.eq(parentId))
                .fetch();
    }
}
