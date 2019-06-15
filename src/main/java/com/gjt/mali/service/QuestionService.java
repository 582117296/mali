package com.gjt.mali.service;

import com.gjt.mali.pojo.Question;
import com.gjt.mali.pojo.QuestionExample;
import com.gjt.mali.vo.PaginationVo;
import com.gjt.mali.vo.QuestionVo;

public interface QuestionService {
//新增问题
    int createQuestion(Question question);
//按照条件查询数据
    PaginationVo queryAllQuestion(QuestionExample questionExample);
//查询数据数量了条数
    int queryQuestionCount(QuestionExample questionExample);
//分页查询
    PaginationVo queryQuestionByUserId(Integer id, Integer page, Integer limit);
//查问题集合
    QuestionVo viewMyQuestion(Integer id);
//查问题
    Question queryQuestionById(Integer id);
//更新
    int updateQuestion(Question question);
//追加阅读数
    void readingNumber(Integer id);
}
