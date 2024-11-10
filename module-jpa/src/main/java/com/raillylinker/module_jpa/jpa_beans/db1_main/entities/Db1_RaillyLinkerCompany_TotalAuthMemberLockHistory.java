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
        name = "total_auth_member_lock_history",
        catalog = "railly_linker_company"
)
@Comment("통합 로그인 계정 계정 정지 히스토리 테이블 (패널티, 휴면계정 등...)")
public class Db1_RaillyLinkerCompany_TotalAuthMemberLockHistory {
    public Db1_RaillyLinkerCompany_TotalAuthMemberLockHistory() {
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
    public Db1_RaillyLinkerCompany_TotalAuthMemberLockHistory(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMember totalAuthMember,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            LocalDateTime lockStart,
            LocalDateTime lockBefore,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Byte lockReasonCode,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String lockReason,
            LocalDateTime earlyRelease
    ) {
        this.totalAuthMember = totalAuthMember;
        this.lockStart = lockStart;
        this.lockBefore = lockBefore;
        this.lockReasonCode = lockReasonCode;
        this.lockReason = lockReason;
        this.earlyRelease = earlyRelease;
    }

    @ManyToOne
    @JoinColumn(name = "total_auth_member_uid", nullable = false)
    @Comment("멤버 고유번호(railly_linker_company.total_auth_member.uid)")
    public Db1_RaillyLinkerCompany_TotalAuthMember totalAuthMember;

    @Column(name = "lock_start", nullable = false, columnDefinition = "DATETIME(3)")
    @Comment("계정 정지 시작 시간")
    public LocalDateTime lockStart;

    @Column(name = "lock_before", columnDefinition = "DATETIME(3)")
    @Comment("계정 정지 만료 시간 (이 시간이 지나기 전까지 계정 정지 상태, null 이라면 무기한 정지, 한번 정해진다면 수정하지 마세요.)")
    public LocalDateTime lockBefore;

    @Column(name = "lock_reason_code", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    @Comment("계정 정지 이유 코드(0 : 기타, 1 : 휴면계정, 2 : 패널티)")
    public Byte lockReasonCode;

    @Column(name = "lock_reason", nullable = false, columnDefinition = "VARCHAR(1000)")
    @Comment("계정 정지 이유 상세(시스템 악용 패널티, 1년 이상 미접속 휴면계정 등...)")
    public String lockReason;

    @Column(name = "early_release", nullable = true, columnDefinition = "DATETIME(3)")
    @Comment("수동으로 계정 정지를 해제한 시간 (이 값이 null 이 아니라면 계정 정지 헤제로 봅니다.)")
    public LocalDateTime earlyRelease;


    // ---------------------------------------------------------------------------------------------
    // [@OneToMany 변수들]
}