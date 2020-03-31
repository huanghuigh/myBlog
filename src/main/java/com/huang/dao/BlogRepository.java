package com.huang.dao;

import com.huang.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 博客dao层
 * @author guangtou
 * @create 2020--02--09--9:00
 */
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    /**
     * 推荐查询
     * 查询blog表中的推荐的
     * @param pageable
     * @return
     */
    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    /**
     * 自定义查询-标题或内容是否包含query
     * @param query
     * @param pageable
     * @return
     */
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    /**
     * 更新查看数目
     * @param id
     * @return
     */
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where id = ?1")
    int updateView(Long id);

    /**
     * 获取博客年份集合
     * @return
     */
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year asc ")
    List<String> findGroupYear();

    /**
     * 获取年份的所有博客集合
     * @param year
     * @return
     */
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}


