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
        name = "total_auth_login_token_history",
        catalog = "railly_linker_company",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"token_type", "access_token", "row_delete_date_str"})
        }
)
@Comment("통합 로그인 계정 인증 토큰 발행 히스토리 테이블")
public class Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory {
    public Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory() {
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
    public Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMember totalAuthMember,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String tokenType,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            LocalDateTime loginDate,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String accessToken,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            LocalDateTime accessTokenExpireWhen,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String refreshToken,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            LocalDateTime refreshTokenExpireWhen,
            @org.jetbrains.annotations.Nullable
            LocalDateTime logoutDate
    ) {
        this.totalAuthMember = totalAuthMember;
        this.tokenType = tokenType;
        this.loginDate = loginDate;
        this.accessToken = accessToken;
        this.accessTokenExpireWhen = accessTokenExpireWhen;
        this.refreshToken = refreshToken;
        this.refreshTokenExpireWhen = refreshTokenExpireWhen;
        this.logoutDate = logoutDate;
    }

    @ManyToOne
    @JoinColumn(name = "total_auth_member_uid", nullable = false)
    @Comment("멤버 고유번호(railly_linker_company.total_auth_member.uid)")
    public Db1_RaillyLinkerCompany_TotalAuthMember totalAuthMember;

    @Column(name = "token_type", nullable = false, columnDefinition = "VARCHAR(50)")
    @Comment("토큰 타입 (ex : Bearer)")
    public String tokenType;

    @Column(name = "login_date", nullable = false, columnDefinition = "DATETIME(3)")
    @Comment("로그인 시간")
    public LocalDateTime loginDate;

    @Column(name = "access_token", nullable = false, columnDefinition = "VARCHAR(500)")
    @Comment("발행된 액세스 토큰")
    public String accessToken;

    @Column(name = "access_token_expire_when", nullable = false, columnDefinition = "DATETIME(3)")
    @Comment("액세스 토큰 만료 일시")
    public LocalDateTime accessTokenExpireWhen;

    @Column(name = "refresh_token", nullable = false, columnDefinition = "VARCHAR(500)")
    @Comment("발행된 리플레시 토큰")
    public String refreshToken;

    @Column(name = "refresh_token_expire_when", nullable = false, columnDefinition = "DATETIME(3)")
    @Comment("리플레시 토큰 만료 일시")
    public LocalDateTime refreshTokenExpireWhen;

    @Column(name = "logout_date", columnDefinition = "DATETIME(3)")
    @Comment("로그아웃 일시")
    public LocalDateTime logoutDate;


    // ---------------------------------------------------------------------------------------------
    // [@OneToMany 변수들]
}