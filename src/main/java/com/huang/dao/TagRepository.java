package com.huang.dao;

import com.huang.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 标签
 * @author guangtou
 * @create 2020--02--08--17:10
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);

    /**
     * 查询所有的标签
     * @param pageable
     * @return
     */
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
