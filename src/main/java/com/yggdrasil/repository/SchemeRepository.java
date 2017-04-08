package com.yggdrasil.repository;

import com.yggdrasil.entity.Scheme;
import com.yggdrasil.entity.SchemePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by yggdrasil on 2017/4/3.
 */
public interface SchemeRepository extends JpaRepository<Scheme,SchemePK> {
    String FIND_IMAGE = "SELECT COMMENT_IMAGE FROM SCHEME WHERE SCHEMEID = ?1 AND row = ?2";

    @Query(value = FIND_IMAGE, nativeQuery = true)
    byte[] findOneOnlyImage(int schemeID, int row);


    List<Scheme> findBySchemeIDOrderByRow(int schemeID);
    @Transactional
    void deleteBySchemeID(int schemeID);

    @Transactional
    void deleteByPlantID(int plantID);
}
