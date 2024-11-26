package com.raillylinker.module_mybatis.mybatis_beans.mybatis1_main.entities;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

//@Table(
//        name = "test_data",
//        catalog = "template"
//)
//@Comment("테스트 정보 테이블(논리적 삭제 적용)")
public class Mybatis1_Template_TestData {
    public Mybatis1_Template_TestData() {
    }

    //    @Id
//    @Column(name = "uid", nullable = false, columnDefinition = "BIGINT UNSIGNED")
//    @Comment("행 고유값")
    public Long uid;

    public Mybatis1_Template_TestData(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            LocalDateTime rowCreateDate,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            LocalDateTime rowUpdateDate,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer randomNum,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            LocalDateTime testDatetime
    ) {
        this.rowCreateDate = rowCreateDate;
        this.rowUpdateDate = rowUpdateDate;
        this.rowDeleteDateStr = rowDeleteDateStr;
        this.content = content;
        this.randomNum = randomNum;
        this.testDatetime = testDatetime;
    }

    //    @Column(name = "row_create_date", nullable = false, columnDefinition = "DATETIME(3)")
//    @Comment("행 생성일")
    public LocalDateTime rowCreateDate;

    //    @Column(name = "row_update_date", nullable = false, columnDefinition = "DATETIME(3)")
//    @Comment("행 수정일")
    public LocalDateTime rowUpdateDate;

    //    @Column(name = "row_delete_date_str", nullable = false, columnDefinition = "VARCHAR(50)")
//    @ColumnDefault("'/'")
//    @Comment("행 삭제일(yyyy_MM_dd_T_HH_mm_ss_SSS_z, 삭제되지 않았다면 /)")
    public String rowDeleteDateStr = "/";

    //    @Column(name = "content", nullable = false, columnDefinition = "VARCHAR(255)")
//    @Comment("테스트 본문")
    public String content;

    //    @Column(name = "random_num", nullable = false, columnDefinition = "INT")
//    @Comment("테스트 랜덤 번호")
    public Integer randomNum;

    //    @Column(name = "test_datetime", nullable = false, columnDefinition = "DATETIME(3)")
//    @Comment("테스트용 일시 데이터")
    public LocalDateTime testDatetime;
}