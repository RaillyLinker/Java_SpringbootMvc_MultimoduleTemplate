package com.raillylinker.module_jpa.jpa_beans.db1_main.repositories_dsl;

import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_Template_FkTestManyToOneChild;
import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_Template_FkTestParent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface Db1_Template_RepositoryDsl {
    // 부모 테이블과 자식 테이블을 조인하여 조회하는 예시
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    List<@Valid @NotNull Db1_Template_FkTestParent> findParentWithChildren();

    // 특정 조건으로 부모-자식 조회 (예: 부모 이름으로 필터링)
    @Valid @NotNull @org.jetbrains.annotations.NotNull
    List<@Valid @NotNull Db1_Template_FkTestParent> findParentWithChildrenByName(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String parentName
    );

    // 부모-자식 테이블의 특정 자식 데이터 조회
    @Valid @NotNull @org.jetbrains.annotations.NotNull
    List<Db1_Template_FkTestManyToOneChild> findChildByParentId(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long parentId
    );
}