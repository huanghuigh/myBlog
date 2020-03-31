package com.huang.web.admin;

import com.huang.pojo.Type;
import com.huang.service.TypeService;
import org.springframework.beans.factory.NamedBean;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 分类控制器
 * @author guangtou
 * @create 2020--02--07--17:52
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

//  跳转到标签主页面
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable,
                                    Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    //    跳转到标签新增页面
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

//    标签修改
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

//    标签新增
    @PostMapping("/types")
    public String postTypes(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes){
//         重复校验
        Type t1 = typeService.getTypeByName(type.getName());
        if ( t1!=null){
            result.rejectValue("name","nameError","标签已存在");
        }
//      错误校验
        if (result.hasErrors()){
            return "admin/types-input";
        }

        Type t = typeService.saveType(type);
//      数据库校验
        if(t !=null){
            redirectAttributes.addFlashAttribute("message","添加标签成功");
            return "redirect:/admin/types";
        }else{
            redirectAttributes.addFlashAttribute("message","添加失败");
            return "admin/types-input";
        }
    }
//    标签修改
    @PostMapping("/types/{id}")
    public String postTypes(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes redirectAttributes){
//         重复校验
        Type t1 = typeService.getTypeByName(type.getName());
        if ( t1!=null){
            result.rejectValue("name","nameError","标签已存在");
        }
//      错误校验
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
//      数据库校验
        if(t !=null){
            redirectAttributes.addFlashAttribute("message","修改标签成功");
            return "redirect:/admin/types";
        }else{
            redirectAttributes.addFlashAttribute("message","修改失败");
            return "admin/types-input";
        }
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除标签成功");
        return "redirect:/admin/types";
    }
}
