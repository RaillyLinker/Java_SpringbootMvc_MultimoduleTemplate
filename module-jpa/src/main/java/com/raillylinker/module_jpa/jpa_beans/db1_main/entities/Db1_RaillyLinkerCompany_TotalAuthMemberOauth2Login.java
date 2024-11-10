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
        name = "total_auth_member_oauth2_login",
        catalog = "railly_linker_company",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"oauth2_type_code", "oauth2_id", "row_delete_date_str"})
        }
)
@Comment("통합 로그인 계정 회원의 OAuth2 로그인 정보 테이블")
public class Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login {
    public Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login() {
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
    public Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMember totalAuthMember,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Byte oauth2TypeCode,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2Id
    ) {
        this.totalAuthMember = totalAuthMember;
        this.oauth2TypeCode = oauth2TypeCode;
        this.oauth2Id = oauth2Id;
    }

    @ManyToOne
    @JoinColumn(name = "total_auth_member_uid", nullable = false)
    @Comment("멤버 고유번호(railly_linker_company.total_auth_member.uid)")
    public Db1_RaillyLinkerCompany_TotalAuthMember totalAuthMember;

    @Column(name = "oauth2_type_code", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    @Comment("oauth2 종류 (1 : GOOGLE, 2 : NAVER, 3 : KAKAO, 4 : APPLE)")
    public Byte oauth2TypeCode;

    @Column(name = "oauth2_id", nullable = false, columnDefinition = "VARCHAR(50)")
    @Comment("OAuth2 로그인으로 얻어온 고유값")
    public String oauth2Id;


    // ---------------------------------------------------------------------------------------------
    // [@OneToMany 변수들]
}