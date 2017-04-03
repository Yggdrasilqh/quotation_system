package com.yggdrasil.repository;

import com.yggdrasil.entity.Scheme;
import com.yggdrasil.entity.SchemePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yggdrasil on 2017/4/3.
 */
public interface SchemeRepository extends JpaRepository<Scheme,SchemePK> {
    List<Scheme> findBySchemeIDOrderByRow(int schemeID);
}
