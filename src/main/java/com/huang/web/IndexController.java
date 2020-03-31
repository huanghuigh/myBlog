package com.huang.web;

import com.huang.exception.MyNotFoundException;
import com.huang.service.BlogService;
import com.huang.service.TagService;
import com.huang.service.TypeService;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author guangtou
 * @create 2020--02--05--12:25
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 博客展示
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/")
    public String hello(@PageableDefault(size=6,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                            Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        System.out.println("aaa:"+tagService.listTagTop(5));
        model.addAttribute("tags",tagService.listTagTop(6));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(6));
        return "index";
    }

    /**
     * 整体博客查询
     * @param pageable
     * @param model
     * @param query
     * @return
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size=6,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                         Model model, @RequestParam String query){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    /**
     * 博客详情页面
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }
    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }
    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
