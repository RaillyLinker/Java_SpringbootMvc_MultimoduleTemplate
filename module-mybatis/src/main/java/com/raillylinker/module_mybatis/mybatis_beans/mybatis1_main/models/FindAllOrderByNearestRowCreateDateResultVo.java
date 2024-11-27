package com.raillylinker.module_mybatis.mybatis_beans.mybatis1_main.models;

import java.time.LocalDateTime;

public class FindAllOrderByNearestRowCreateDateResultVo {
    public FindAllOrderByNearestRowCreateDateResultVo() {
    }

    //    @Id
//    @Column(name = "uid", nullable = false, columnDefinition = "BIGINT UNSIGNED")
//    @Comment("행 고유값")
    public Long uid;

    //    @Column(name = "row_create_date", nullable = false, columnDefinition = "DATETIME(3)")
//    @Comment("행 생성일")
    public LocalDateTime rowCreateDate;

    //    @Column(name = "row_update_date", nullable = false, columnDefinition = "DATETIME(3)")
//    @Comment("행 수정일")
    public LocalDateTime rowUpdateDate;

    //    @Column(name = "content", nullable = false, columnDefinition = "VARCHAR(255)")
//    @Comment("테스트 본문")
    public String content;

    //    @Column(name = "random_num", nullable = false, columnDefinition = "INT")
//    @Comment("테스트 랜덤 번호")
    public Integer randomNum;

    //    @Column(name = "test_datetime", nullable = false, columnDefinition = "DATETIME(3)")
//    @Comment("테스트용 일시 데이터")
    public LocalDateTime testDatetime;

    public Long timeDiffMicroSec;
}