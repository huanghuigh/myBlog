package com.huang.service;

import com.huang.dao.TagRepository;
import com.huang.exception.MyNotFoundException;
import com.huang.pojo.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guangtou
 * @create 2020--02--08--17:16
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }
    @Transactional
    @Override
    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }
    @Transactional
    @Override
    public Tag updateTag(Long id,Tag tag) {
        Tag t  = tagRepository.getOne(id);
        if (t==null){
            throw new MyNotFoundException("Tag更新失败");
        }
        BeanUtils.copyProperties(tag,t);
        return tag;
    }
    @Transactional
    @Override
    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }
    @Transactional
    @Override
    public Tag getTag(long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(ids!=null&&!"".equals(ids)){
            String [] idarray = ids.split(",");
            for (int i=0;i<idarray.length;i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=  Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable =  PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
    }
}
