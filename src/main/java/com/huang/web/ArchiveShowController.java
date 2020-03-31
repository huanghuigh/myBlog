package com.huang.web;

import com.huang.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author guangtou
 * @create 2020--02--13--11:29
 */
@Controller
public class ArchiveShowController {
    @Autowired
    private BlogService blogService;
    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }

}
