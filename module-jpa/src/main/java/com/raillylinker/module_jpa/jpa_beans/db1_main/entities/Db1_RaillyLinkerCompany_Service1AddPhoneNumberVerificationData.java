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
        name = "service1_add_phone_number_verification_data",
        catalog = "railly_linker_company"
)
@Comment("Service1 계정 전화번호 추가하기 검증 테이블")
public class Db1_RaillyLinkerCompany_Service1AddPhoneNumberVerificationData {
    public Db1_RaillyLinkerCompany_Service1AddPhoneNumberVerificationData() {
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
    public Db1_RaillyLinkerCompany_Service1AddPhoneNumberVerificationData(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_Service1MemberData service1MemberData,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationSecret,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            LocalDateTime verificationExpireWhen
    ) {
        this.service1MemberData = service1MemberData;
        this.phoneNumber = phoneNumber;
        this.verificationSecret = verificationSecret;
        this.verificationExpireWhen = verificationExpireWhen;
    }

    @ManyToOne
    @JoinColumn(name = "service1_member_uid", nullable = false)
    @Comment("멤버 고유번호(railly_linker_company.service1_member_data.uid)")
    public Db1_RaillyLinkerCompany_Service1MemberData service1MemberData;

    @Column(name = "phone_number", nullable = false, columnDefinition = "VARCHAR(45)")
    @Comment("전화 번호")
    public String phoneNumber;

    @Column(name = "verification_secret", nullable = false, columnDefinition = "VARCHAR(20)")
    @Comment("검증 비문")
    public String verificationSecret;

    @Column(name = "verification_expire_when", nullable = false, columnDefinition = "DATETIME(3)")
    @Comment("검증 만료 일시")
    public LocalDateTime verificationExpireWhen;


    // ---------------------------------------------------------------------------------------------
    // [@OneToMany 변수들]
}