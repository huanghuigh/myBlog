package com.huang.service;

import com.huang.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author guangtou
 * @create 2020--02--08--17:12
 */
public interface TagService {

    public Tag saveTag(Tag tag);

    public void deleteTag(Tag tag);

    public Tag updateTag(Long id,Tag tag);

    public void deleteTagById(Long id);

    public Tag getTag(long id);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    public Page<Tag> listTag(Pageable pageable);

    public Tag getTagByName(String name);

    /**
     * 查询所有标签
     * @return
     */
    public List<Tag> listTag();

    /**
     * 查询多个id的标签
     * @param ids
     * @return
     */
    public List<Tag> listTag(String ids);

    /**
     * 查询size个，由大到小
     * @param size
     * @return
     */
    List<Tag> listTagTop(Integer size);
}
