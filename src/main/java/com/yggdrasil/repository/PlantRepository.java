package com.yggdrasil.repository;

import com.yggdrasil.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yggdrasil on 2017/3/31.
 */
public interface PlantRepository extends JpaRepository<Plant,Integer> {

}
