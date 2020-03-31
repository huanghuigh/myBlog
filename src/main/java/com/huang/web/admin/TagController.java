package com.huang.web.admin;

import com.huang.pojo.Tag;
import com.huang.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author guangtou
 * @create 2020--02--08--17:35
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 跳转至标签页面
     * @return
     */
    @GetMapping("/tags")
    public String goTags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                     Pageable pageable,
                         Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    /**
     * 跳转至标签添加页面
     * @return
     */
    @GetMapping("/tags/input")
    public String goTagsInput(Model model){
        model.addAttribute("tags",new Tag());
        return "admin/tags-input";
    }

    /**
     * 跳转至标签添加页面
     * @param id 标签id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String goTagsInputAndId(@PathVariable Long id,Model model){
        model.addAttribute("tags",tagService.getTag(id));
        return "admin/tags-input";
    };
    /**
     * 添加标签
     * @param tag 标签
     * @param result
     * @param redirectAttributes
     */
    @PostMapping("/tags")
    public String postTags(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes){
        Tag t1 = tagService.getTagByName(tag.getName());
        if(t1!=null){

            result.rejectValue("name","nameError","标签分类已存在");
        }
        if (result.hasErrors()){
            return "redirect:/admin/tags-input";
        }
        Tag t2 = tagService.saveTag(tag);
        if (t2!=null){
            redirectAttributes.addFlashAttribute("message","操作成功");
            return "redirect:/admin/tags";
        }else {
            redirectAttributes.addFlashAttribute("message","操作失败");
            return "redirect:/admin/tags-input";
        }
    }
    /**
     * 修改标签
     * @param tag
     * @param result
     * @param id
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/tags/{id}")
    public String postTags(@Valid Tag tag, BindingResult result,@PathVariable long id, RedirectAttributes redirectAttributes){
        Tag t1 = tagService.getTagByName(tag.getName());
        if(t1!=null){
            result.rejectValue("name","nameError","标签分类已存在");
        }
        if (result.hasErrors()){
            return "redirect:/admin/tags-input";
        }

        Tag t2 = tagService.updateTag(id,tag);
        if (t2!=null){
            redirectAttributes.addFlashAttribute("message","操作成功");
            return "redirect:/admin/tags";
        }else {
            redirectAttributes.addFlashAttribute("message","操作失败");
            return "redirect:/admin/tags-input";
        }
    }

    /**
     * 删除标签
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/delete")
    public String deleteTags(@PathVariable Long id, Model model){
        tagService.deleteTagById(id);
        model.addAttribute("message","删除标签成功");
        return "redirect:/admin/tags";
    }

}
