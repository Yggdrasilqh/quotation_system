package com.yggdrasil.repository;

import com.yggdrasil.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by yggdrasil on 2017/3/31.
 */
public interface PlantRepository extends JpaRepository<Plant, Integer> {
    String FIND_IMAGE = "SELECT IMAGE FROM PLANT WHERE ID = ?1";

    @Query(value = FIND_IMAGE, nativeQuery = true)
    byte[] findOneOnlyImage(int id);

    List<Plant> findByType(String type);

    @Transactional
    void deleteByType(String type);
}
