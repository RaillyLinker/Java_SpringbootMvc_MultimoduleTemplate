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
        name = "service1_member_data",
        catalog = "railly_linker_company",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"account_id", "row_delete_date_str"})
        }
)
@Comment("Service1 계정 회원 정보 테이블")
public class Db1_RaillyLinkerCompany_Service1MemberData {
    public Db1_RaillyLinkerCompany_Service1MemberData() {
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
    public Db1_RaillyLinkerCompany_Service1MemberData(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String accountId,
            String accountPassword,
            Db1_RaillyLinkerCompany_Service1MemberProfileData frontService1MemberProfileData,
            Db1_RaillyLinkerCompany_Service1MemberEmailData frontService1MemberEmailData,
            Db1_RaillyLinkerCompany_Service1MemberPhoneData frontService1MemberPhoneData
    ) {
        this.accountId = accountId;
        this.accountPassword = accountPassword;
        this.frontService1MemberProfileData = frontService1MemberProfileData;
        this.frontService1MemberEmailData = frontService1MemberEmailData;
        this.frontService1MemberPhoneData = frontService1MemberPhoneData;
    }

    @Column(name = "account_id", nullable = false, columnDefinition = "VARCHAR(100)")
    @Comment("계정 아이디")
    public String accountId;

    @Column(name = "account_password", columnDefinition = "VARCHAR(100)")
    @Comment("계정 로그인시 사용하는 비밀번호 (계정 아이디, 이메일, 전화번호 로그인에 모두 사용됨. OAuth2 만 등록했다면 null)")
    public String accountPassword;

    @ManyToOne
    @JoinColumn(name = "front_service1_member_profile_uid")
    @Comment("대표 프로필 Uid (railly_linker_company.service1_member_profile_data.uid)")
    public Db1_RaillyLinkerCompany_Service1MemberProfileData frontService1MemberProfileData;

    @ManyToOne
    @JoinColumn(name = "front_service1_member_email_uid")
    @Comment("대표 이메일 Uid (railly_linker_company.service1_member_email_data.uid)")
    public Db1_RaillyLinkerCompany_Service1MemberEmailData frontService1MemberEmailData;

    @ManyToOne
    @JoinColumn(name = "front_service1_member_phone_uid")
    @Comment("대표 전화번호 Uid (railly_linker_company.service1_member_phone_data.uid)")
    public Db1_RaillyLinkerCompany_Service1MemberPhoneData frontService1MemberPhoneData;


    // ---------------------------------------------------------------------------------------------
    // [@OneToMany 변수들]
    @OneToMany(
            mappedBy = "service1MemberData",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_Service1MemberRoleData> service1MemberRoleDataList;

    @OneToMany(
            mappedBy = "service1MemberData",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_Service1MemberProfileData> service1MemberProfileDataList;

    @OneToMany(
            mappedBy = "service1MemberData",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_Service1MemberPhoneData> service1MemberPhoneDataList;

    @OneToMany(
            mappedBy = "service1MemberData",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_Service1MemberOauth2LoginData> service1MemberOauth2LoginDataList;

    @OneToMany(
            mappedBy = "service1MemberData",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_Service1MemberEmailData> service1MemberEmailDataList;

    @OneToMany(
            mappedBy = "service1MemberData",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_Service1MemberLockHistory> service1MemberLockHistoryList;

    @OneToMany(
            mappedBy = "service1MemberData",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_Service1LogInTokenHistory> service1LogInTokenHistoryList;

    @OneToMany(
            mappedBy = "service1MemberData",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_Service1AddPhoneNumberVerificationData> service1AddPhoneNumberVerificationDataList;

    @OneToMany(
            mappedBy = "service1MemberData",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_Service1AddEmailVerificationData> service1AddEmailVerificationDataList;
}