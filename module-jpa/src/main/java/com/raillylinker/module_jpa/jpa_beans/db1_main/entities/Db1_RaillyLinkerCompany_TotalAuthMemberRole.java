package com.raillylinker.module_jpa.jpa_beans.db1_main.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "total_auth_member_role",
        catalog = "railly_linker_company",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"total_auth_member_uid", "role", "row_delete_date_str"})
        }
)
@Comment("통합 로그인 계정 회원 권한 정보 테이블")
public class Db1_RaillyLinkerCompany_TotalAuthMemberRole {
    public Db1_RaillyLinkerCompany_TotalAuthMemberRole() {
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

    @Column(name = "row_delete_date_str", nullable = false, columnDefinition = "VARCHAR(50)")
    @ColumnDefault("'/'")
    @Comment("행 삭제일(yyyy_MM_dd_T_HH_mm_ss_SSS_z, 삭제되지 않았다면 /)")
    public String rowDeleteDateStr = "/";


    // ---------------------------------------------------------------------------------------------
    // [입력값 수동 입력 변수들]
    public Db1_RaillyLinkerCompany_TotalAuthMemberRole(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMember totalAuthMember,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String role
    ) {
        this.totalAuthMember = totalAuthMember;
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "total_auth_member_uid", nullable = false)
    @Comment("멤버 고유번호(railly_linker_company.total_auth_member.uid)")
    public Db1_RaillyLinkerCompany_TotalAuthMember totalAuthMember;

    @Column(name = "role", nullable = false, columnDefinition = "VARCHAR(100)")
    @Comment("권한 코드 (ROLE_{권한} 형식으로 저장합니다.) (ex : (관리자 : ROLE_ADMIN, 개발자 : ROLE_DEVELOPER))")
    public String role;


    // ---------------------------------------------------------------------------------------------
    // [@OneToMany 변수들]
}