package com.hackathon.backend.repository;

import com.hackathon.backend.model.FavoriteProducts;
import com.hackathon.backend.util.LabelValue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteProductRepository extends CrudRepository<FavoriteProducts, Long> {

    @Query("""
            SELECT new com.hackathon.backend.util.LabelValue(FP.id, FP.name)
            FROM FavoriteProducts FP WHERE FP.user.id = ?1
            """)
    List<LabelValue> getAllLabelValueFavoriteProductForUser(Long userId);
}