package com.raillylinker.module_jpa.jpa_beans.db1_main.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "total_auth_member",
        catalog = "railly_linker_company",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"account_id", "row_delete_date_str"})
        }
)
@Comment("통합 로그인 계정 회원 정보 테이블")
public class Db1_RaillyLinkerCompany_TotalAuthMember {
    public Db1_RaillyLinkerCompany_TotalAuthMember() {
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
    public Db1_RaillyLinkerCompany_TotalAuthMember(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String accountId,
            @org.jetbrains.annotations.Nullable
            String accountPassword,
            @org.jetbrains.annotations.Nullable
            Db1_RaillyLinkerCompany_TotalAuthMemberProfile frontTotalAuthMemberProfile,
            @org.jetbrains.annotations.Nullable
            Db1_RaillyLinkerCompany_TotalAuthMemberEmail frontTotalAuthMemberEmail,
            @org.jetbrains.annotations.Nullable
            Db1_RaillyLinkerCompany_TotalAuthMemberPhone frontTotalAuthMemberPhone
    ) {
        this.accountId = accountId;
        this.accountPassword = accountPassword;
        this.frontTotalAuthMemberProfile = frontTotalAuthMemberProfile;
        this.frontTotalAuthMemberEmail = frontTotalAuthMemberEmail;
        this.frontTotalAuthMemberPhone = frontTotalAuthMemberPhone;
    }

    @Column(name = "account_id", nullable = false, columnDefinition = "VARCHAR(100)")
    @Comment("계정 아이디")
    public String accountId;

    @Column(name = "account_password", columnDefinition = "VARCHAR(100)")
    @Comment("계정 로그인시 사용하는 비밀번호 (계정 아이디, 이메일, 전화번호 로그인에 모두 사용됨. OAuth2 만 등록했다면 null)")
    public String accountPassword;

    @ManyToOne
    @JoinColumn(name = "front_total_auth_member_profile_uid")
    @Comment("대표 프로필 Uid (railly_linker_company.total_auth_member_profile.uid)")
    public Db1_RaillyLinkerCompany_TotalAuthMemberProfile frontTotalAuthMemberProfile;

    @ManyToOne
    @JoinColumn(name = "front_total_auth_member_email_uid")
    @Comment("대표 이메일 Uid (railly_linker_company.total_auth_member_email.uid)")
    public Db1_RaillyLinkerCompany_TotalAuthMemberEmail frontTotalAuthMemberEmail;

    @ManyToOne
    @JoinColumn(name = "front_total_auth_member_phone_uid")
    @Comment("대표 전화번호 Uid (railly_linker_company.total_auth_member_phone.uid)")
    public Db1_RaillyLinkerCompany_TotalAuthMemberPhone frontTotalAuthMemberPhone;


    // ---------------------------------------------------------------------------------------------
    // [@OneToMany 변수들]
    @OneToMany(
            mappedBy = "totalAuthMember",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_TotalAuthMemberRole> totalAuthMemberRoleList;

    @OneToMany(
            mappedBy = "totalAuthMember",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_TotalAuthMemberProfile> totalAuthMemberProfileList;

    @OneToMany(
            mappedBy = "totalAuthMember",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_TotalAuthMemberPhone> totalAuthMemberPhoneList;

    @OneToMany(
            mappedBy = "totalAuthMember",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login> totalAuthMemberOauth2LoginList;

    @OneToMany(
            mappedBy = "totalAuthMember",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_TotalAuthMemberEmail> totalAuthMemberEmailList;

    @OneToMany(
            mappedBy = "totalAuthMember",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_TotalAuthMemberLockHistory> totalAuthMemberLockHistoryList;

    @OneToMany(
            mappedBy = "totalAuthMember",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory> totalAuthLogInTokenHistoryList;

    @OneToMany(
            mappedBy = "totalAuthMember",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_TotalAuthAddPhoneVerification> totalAuthAddPhoneVerificationList;

    @OneToMany(
            mappedBy = "totalAuthMember",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_TotalAuthAddEmailVerification> totalAuthAddEmailVerificationList;
}