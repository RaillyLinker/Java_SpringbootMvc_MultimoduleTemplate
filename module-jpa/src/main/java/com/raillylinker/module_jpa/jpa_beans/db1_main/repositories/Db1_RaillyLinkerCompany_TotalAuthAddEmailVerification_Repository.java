package com.raillylinker.module_jpa.jpa_beans.db1_main.repositories;

import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_RaillyLinkerCompany_TotalAuthAddEmailVerification;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Db1_RaillyLinkerCompany_TotalAuthAddEmailVerification_Repository extends JpaRepository<Db1_RaillyLinkerCompany_TotalAuthAddEmailVerification, Long> {
    Db1_RaillyLinkerCompany_TotalAuthAddEmailVerification findByUidAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long uid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );
}
