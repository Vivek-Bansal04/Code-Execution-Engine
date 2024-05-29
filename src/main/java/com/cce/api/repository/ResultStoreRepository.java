package com.cce.api.repository;

import com.cce.api.entity.ResultStore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultStoreRepository extends MongoRepository<ResultStore,Integer> {
}
