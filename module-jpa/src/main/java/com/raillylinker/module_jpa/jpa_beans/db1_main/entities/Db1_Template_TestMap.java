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
        name = "test_map",
        catalog = "template"
)
@Comment("지도 좌표 테스트용 테이블")
public class Db1_Template_TestMap {
    public Db1_Template_TestMap() {
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
    public Db1_Template_TestMap(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double latitude,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double longitude
    ) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Column(name = "latitude", nullable = false, columnDefinition = "DOUBLE")
    @Comment("지도 위도")
    public Double latitude;

    @Column(name = "longitude", nullable = false, columnDefinition = "DOUBLE")
    @Comment("지도 경도")
    public Double longitude;


    // ---------------------------------------------------------------------------------------------
    // [@OneToMany 변수들]
}