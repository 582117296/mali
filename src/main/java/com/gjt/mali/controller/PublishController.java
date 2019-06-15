package com.gjt.mali.controller;

import com.gjt.mali.exception.ExceptionEnums;
import com.gjt.mali.exception.MaLiException;
import com.gjt.mali.pojo.Question;
import com.gjt.mali.pojo.User;
import com.gjt.mali.service.HomeService;
import com.gjt.mali.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    private HomeService homeService;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable Integer id, Model model){
        if (id==null){
            throw new MaLiException(ExceptionEnums.THIS_QUESTION_DOES_NOT_EXIST);
        }else {
            Question question=questionService.queryQuestionById(id);
            model.addAttribute("question",question);
        }
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value ="description",required = false) String description,
                            @RequestParam(value ="tag",required = false) String tag,
                            @RequestParam(value ="id",required = false) Integer id,
                            HttpServletRequest request,
                            Model model){
        Question question=new Question();
        question.setTitle(title);
        question.setTag(tag);
        question.setDescription(description);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){//没有登录
            model.addAttribute("error", "您还未登录!请先登录!");
            System.out.println("cookie为空");
            return "publish";
        }else {
            if (title==null || title.equals("")){
                model.addAttribute("error", "标题不能为空");
                return "publish";
            }
            if (description==null || description.equals("")){
                model.addAttribute("error", "问题描述不能为空");
                return "publish";
            }
            if (tag==null || tag.equals("")){
                model.addAttribute("error", "类别标签不能为空");
                return "publish";
            }
            question.setUserId(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModifiled(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            int num;
            if(id!=null){
                question.setId(id);
                num=questionService.updateQuestion(question);
                return  num>0?  "redirect:/profile/questions": "publish";
            }
            num = questionService.createQuestion(question);
            if (num < 0) {
                model.addAttribute("question", question);
                request.getSession().setAttribute("error", "您的问题发布失败!请尝试重新发布!");
                return "publish";
            }
        }
            model.addAttribute("success", "发布成功!");
            return "redirect:/";
    }
}
