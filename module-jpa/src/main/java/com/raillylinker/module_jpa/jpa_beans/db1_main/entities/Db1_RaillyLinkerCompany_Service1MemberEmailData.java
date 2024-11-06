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
        name = "service1_member_email_data",
        catalog = "railly_linker_company",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email_address", "row_delete_date_str"})
        }
)
@Comment("Service1 계정 회원 이메일 정보 테이블")
public class Db1_RaillyLinkerCompany_Service1MemberEmailData {
    public Db1_RaillyLinkerCompany_Service1MemberEmailData() {
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
    public Db1_RaillyLinkerCompany_Service1MemberEmailData(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_Service1MemberData service1MemberData,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String emailAddress
    ) {
        this.service1MemberData = service1MemberData;
        this.emailAddress = emailAddress;
    }

    @ManyToOne
    @JoinColumn(name = "service1_member_uid", nullable = false)
    @Comment("멤버 고유번호(railly_linker_company.service1_member_data.uid)")
    public Db1_RaillyLinkerCompany_Service1MemberData service1MemberData;

    @Column(name = "email_address", nullable = false, columnDefinition = "VARCHAR(100)")
    @Comment("이메일 주소 (중복 비허용)")
    public String emailAddress;


    // ---------------------------------------------------------------------------------------------
    // [@OneToMany 변수들]
    @OneToMany(
            mappedBy = "frontService1MemberEmailData",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    public List<Db1_RaillyLinkerCompany_Service1MemberData> service1MemberDataList;
}