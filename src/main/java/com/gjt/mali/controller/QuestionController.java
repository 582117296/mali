package com.gjt.mali.controller;

import com.gjt.mali.service.CommentService;
import com.gjt.mali.service.QuestionService;
import com.gjt.mali.utils.CommentTypeEnum;
import com.gjt.mali.vo.CommentVo;
import com.gjt.mali.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String viewQuestion(@PathVariable Integer id,
                               Model model){
        QuestionVo questionVo=questionService.viewMyQuestion(id);
        questionService.readingNumber(id);
        List<CommentVo> commentVoList=commentService.queryAllcommentByParentId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("questionVo", questionVo);
        model.addAttribute("commentVo", commentVoList);
        return "question";
    }

}
