package com.huang.web;

import com.huang.pojo.Tag;
import com.huang.service.BlogService;
import com.huang.service.TagService;
import com.huang.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 分类页面
 * @author guangtou
 * @create 2020--02--13--9:22
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size=6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model, @PathVariable Long id){
        List<Tag> tagList = tagService.listTagTop(100);
//        如果是从导航跳转过来则给默认最多的那个id
        if(id == -1){
            id = tagList.get(0).getId();
        }
        model.addAttribute("tags",tagList);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
