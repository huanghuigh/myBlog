package com.huang.dao;

import com.huang.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 分类dao层
 * @author guangtou
 * @create 2020--02--07--17:20
 */
public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);

    /**
     * 查询所有分类
     * @param pageable
     * @return
     */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
