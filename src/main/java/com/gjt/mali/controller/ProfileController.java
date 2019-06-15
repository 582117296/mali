package com.gjt.mali.controller;

import com.gjt.mali.pojo.User;
import com.gjt.mali.service.QuestionService;
import com.gjt.mali.vo.PaginationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller

public class ProfileController {


    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String doProfile(@PathVariable String action, Model model,
                            @RequestParam(name = "page", defaultValue = "1") Integer page,
                            @RequestParam(name = "limit",defaultValue = "5") Integer limit,
                            HttpServletRequest request){
        switch (action){
            case "questions" :
                model.addAttribute("section", "questions");
                model.addAttribute("sectionName", "我的问题");
                break;
            case  "reply":
                model.addAttribute("section", "reply");
                model.addAttribute("sectionName", "最新回复");
                break;
        }
        User user = (User) request.getSession().getAttribute("user");
        PaginationVo paginationVo=questionService.queryQuestionByUserId(user.getId(),page,limit);
        model.addAttribute("paginationVo", paginationVo);
        return "profile";
    }
}
