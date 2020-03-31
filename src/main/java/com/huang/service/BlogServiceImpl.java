package com.huang.service;

import com.huang.dao.BlogRepository;
import com.huang.exception.MyNotFoundException;
import com.huang.pojo.Blog;
import com.huang.pojo.Type;
import com.huang.util.MarkdownUtils;
import com.huang.util.MyBeanUtils;
import com.huang.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author guangtou
 * @create 2020--02--09--9:37
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        if(blog == null){
            throw new MyNotFoundException("该博客不存在");
        }
        Blog b  = new Blog();
//        将blog拷贝至b中
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
//        将内容换为html格式
        b.setContent(MarkdownUtils.markdownToHtmlExtension(content));

        blogRepository.updateView(id);
        return b;
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        if (b!=null){
            //  将blog拷贝至b，不拷贝null的元素
            BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
            b.setUpdateTime(new Date());
            return blogRepository.save(b);
        }else {
            throw new MyNotFoundException("博客更新失败");
        }
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
//              是否包含标题
                if (blog.getTitle() != null && !"".equals(blog.getTitle())) {
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
//               是否包含分类
                if(blog.getTypeId()!=null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
//               是否推荐
                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> BlogAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable =  PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
//        获取所有年份
        List<String> years = blogRepository.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year :years){
//            将该年份的所有博客保存到map中
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }
}
