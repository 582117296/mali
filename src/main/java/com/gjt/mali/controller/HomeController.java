package com.gjt.mali.controller;

import com.gjt.mali.pojo.QuestionExample;
import com.gjt.mali.service.HomeService;
import com.gjt.mali.service.QuestionService;
import com.gjt.mali.vo.PaginationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String getLogin(HttpServletRequest request,
                           Model model,
                           @RequestParam(name = "page", defaultValue = "1") Integer page,
                           @RequestParam(name = "limit",defaultValue = "5") Integer limit,
                           @RequestParam(name = "question",required=false) String question){
        QuestionExample questionExample=new QuestionExample();
        if (question!=null && !question.equals("")){
            questionExample.createCriteria().andTitleLike("%"+question+"%");
        }
        Integer totalCount=questionService.queryQuestionCount(questionExample);
        if (page<1){
            page=1;
        }
        if (page*limit>totalCount && totalCount % limit !=0){
            page=totalCount/limit +1;
        }
        int count=limit*(page-1);
        questionExample.setOrderByClause("id DESC");
        questionExample.setPage(count);
        questionExample.setLimit(limit);
        PaginationVo paginationVo=questionService.queryAllQuestion(questionExample);
        paginationVo.setPagination(totalCount, page, limit);
        model.addAttribute("paginationVo", paginationVo);
        return "index";
    }
    @GetMapping("/exit")
    public String doExit(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().invalidate();
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }


}
