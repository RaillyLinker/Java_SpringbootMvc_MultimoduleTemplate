package com.raillylinker.module_jpa.jpa_beans.db1_main.repositories;

import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_RaillyLinkerCompany_TotalAuthMember;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Db1_RaillyLinkerCompany_TotalAuthMember_Repository extends JpaRepository<Db1_RaillyLinkerCompany_TotalAuthMember, Long> {
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Boolean existsByAccountIdAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String accountId,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );

    Optional<Db1_RaillyLinkerCompany_TotalAuthMember> findByUidAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long uid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Optional<Db1_RaillyLinkerCompany_TotalAuthMember> findByAccountIdAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String accountId,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );
}