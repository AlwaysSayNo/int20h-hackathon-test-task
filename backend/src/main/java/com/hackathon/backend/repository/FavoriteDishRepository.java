package com.hackathon.backend.repository;

import com.hackathon.backend.model.FavoriteDishes;
import com.hackathon.backend.util.LabelValue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteDishRepository extends CrudRepository<FavoriteDishes, Long> {

    @Query("""
            SELECT new com.hackathon.backend.util.LabelValue(FD.id, FD.name)
            FROM FavoriteDishes FD WHERE FD.user.id = ?1
            """)
    List<LabelValue> getAllLabelValueFavoriteDishForUser(Long userId);
}
