package com.raillylinker.module_jpa.jpa_beans.db1_main.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "fk_test_many_to_one_child",
        catalog = "template"
)
@Comment("Foreign Key 테스트용 테이블 (one to many 테스트용 자식 테이블)")
public class Db1_Template_FkTestManyToOneChild {
    public Db1_Template_FkTestManyToOneChild() {
    }

    // [기본 입력값이 존재하는 변수들]
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    @Comment("행 고유값")
    public Long uid;

    @Column(name = "row_create_date", nullable = false, columnDefinition = "DATETIME(3)")
    @CreationTimestamp
    @Comment("행 생성일")
    public LocalDateTime rowCreateDate;

    @Column(name = "row_update_date", nullable = false, columnDefinition = "DATETIME(3)")
    @UpdateTimestamp
    @Comment("행 수정일")
    public LocalDateTime rowUpdateDate;


    // ---------------------------------------------------------------------------------------------
    // [입력값 수동 입력 변수들]
    public Db1_Template_FkTestManyToOneChild(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String childName,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_Template_FkTestParent fkTestParent
    ) {
        this.childName = childName;
        this.fkTestParent = fkTestParent;
    }

    @Column(name = "child_name", nullable = false, columnDefinition = "VARCHAR(255)")
    @Comment("자식 테이블 이름")
    public String childName;

    @ManyToOne
    @JoinColumn(name = "fk_test_parent_uid", nullable = false)
    @Comment("FK 부모 테이블 고유번호 (template.fk_test_parent.uid)")
    public Db1_Template_FkTestParent fkTestParent;


    // ---------------------------------------------------------------------------------------------
    // [@OneToMany 변수들]
}