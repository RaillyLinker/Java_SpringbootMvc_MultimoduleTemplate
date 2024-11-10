package com.raillylinker.module_mongodb.mongodb_beans.mdb1_main.repositories;

import com.raillylinker.module_mongodb.mongodb_beans.mdb1_main.documents.Mdb1Test;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Mdb1TestRepository extends MongoRepository<Mdb1Test, String> {
}