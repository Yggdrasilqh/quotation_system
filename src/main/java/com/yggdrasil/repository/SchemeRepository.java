package com.yggdrasil.repository;

import com.yggdrasil.entity.Scheme;
import com.yggdrasil.entity.SchemePK;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yggdrasil on 2017/4/3.
 */
public interface SchemeRepository extends JpaRepository<Scheme,SchemePK> {
}
