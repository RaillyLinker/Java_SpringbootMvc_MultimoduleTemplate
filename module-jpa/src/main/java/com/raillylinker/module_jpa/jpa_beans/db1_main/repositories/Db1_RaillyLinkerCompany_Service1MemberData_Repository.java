package com.raillylinker.module_jpa.jpa_beans.db1_main.repositories;

import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_RaillyLinkerCompany_Service1MemberData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface Db1_RaillyLinkerCompany_Service1MemberData_Repository extends JpaRepository<Db1_RaillyLinkerCompany_Service1MemberData, Long> {
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Boolean existsByAccountIdAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String accountId,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Optional<Db1_RaillyLinkerCompany_Service1MemberData> findByAccountIdAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String accountId,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );
}