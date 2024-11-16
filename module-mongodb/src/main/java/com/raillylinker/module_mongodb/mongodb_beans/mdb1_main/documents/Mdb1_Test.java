package com.raillylinker.module_mongodb.mongodb_beans.mdb1_main.documents;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "test")
public class Mdb1_Test {
    @Id
    public String uid;

    @CreatedDate
    @Field("row_create_date")
    public LocalDateTime rowCreateDate;

    @LastModifiedDate
    @Field("row_update_date")
    public LocalDateTime rowUpdateDate;


    // ---------------------------------------------------------------------------------------------
    // [입력값 수동 입력 변수들]
    public Mdb1_Test(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer randomNum,
            @Nullable @org.jetbrains.annotations.Nullable
            String nullableValue,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean rowActivate
    ) {
        this.content = content;
        this.randomNum = randomNum;
        this.nullableValue = nullableValue;
        this.rowActivate = rowActivate;
    }

    @Field("content")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public String content;

    @Field("random_num")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Integer randomNum;

    @Field("nullable_value")
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String nullableValue;

    @Field("row_activate")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Boolean rowActivate;
}
