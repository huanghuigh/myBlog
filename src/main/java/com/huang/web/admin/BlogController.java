package com.huang.web.admin;

import com.huang.pojo.Blog;
import com.huang.pojo.User;
import com.huang.service.BlogService;
import com.huang.service.TagService;
import com.huang.service.TypeService;
import com.huang.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 博客主页控制器
 * @author guangtou
 * @create 2020--02--07--11:50
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    /*博客发布页*/
    private static final String INPUT = "admin/blogs-input";
    /*博客首页*/
    private static final String LIST = "admin/blogs";
    /*重定向至博客首页*/
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";
    /*重定向至博客发布页*/
    private static final String REDIRECT_INPUT = "redirect:/admin/blogs-input";


    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 博客首页
     * 显示10条，按照更新时间，倒序
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        // 分类，用于下拉栏选择
        model.addAttribute("types", typeService.listType());
        // 博客
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

    /**
     * 博客ajax查询
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                         BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        // 只刷新blogList区域
        return "admin/blogs :: blogList";
    }
    @GetMapping("/blogs/input")
    public String blogsInput(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    /**
     * 博客提交
     * @param blog
     * @param redirectAttributes
     * @param session
     * @return
     */
    @PostMapping("blogs")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession session){
        blog.setUser((User)session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b ;
        if (blog.getId()!=null){
             b = blogService.updateBlog(blog.getId(),blog);
        }else {
             b = blogService.saveBlog(blog);
        }

        if (b==null){
            redirectAttributes.addFlashAttribute("message","操作失败");
        }else{
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }

    /**
     * 获取博客信息，跳转至博客修改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }
    private void setTypeAndTag(Model model){
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.listType());
    }

    /**
     * 删除博客
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","操作成功");
        return REDIRECT_LIST;
    }
}
