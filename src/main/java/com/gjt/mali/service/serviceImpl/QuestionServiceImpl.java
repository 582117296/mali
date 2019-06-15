package com.gjt.mali.service.serviceImpl;

import com.gjt.mali.exception.ExceptionEnums;
import com.gjt.mali.exception.MaLiException;
import com.gjt.mali.mapper.QuestionMapper;
import com.gjt.mali.mapper.UserMapper;
import com.gjt.mali.pojo.Question;
import com.gjt.mali.pojo.QuestionExample;
import com.gjt.mali.pojo.User;
import com.gjt.mali.service.QuestionService;
import com.gjt.mali.vo.PaginationVo;
import com.gjt.mali.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public int updateQuestion(Question question) {
        return questionMapper.updateByPrimaryKey(question);
    }

    @Override
    public int createQuestion(Question question) {
        int insert = questionMapper.insert(question);
        if (insert<=0){
            throw new MaLiException(ExceptionEnums.INSERT_DOES_NOT);
        }
        return insert;
    }

    @Override
    public PaginationVo queryAllQuestion(QuestionExample questionExample) {
        List<QuestionVo> questionVoList=new ArrayList<>();
        List<Question> questionList = questionMapper.selectByExample(questionExample);
        PaginationVo paginationVo=new PaginationVo();
        return processVo(questionList, userMapper, questionVoList,paginationVo);

    }
    @Override
    public int queryQuestionCount(QuestionExample questionExample) {
        return questionMapper.countByExample(questionExample);
    }

    @Override
    public PaginationVo queryQuestionByUserId(Integer userId, Integer page, Integer limit) {
        List<QuestionVo> questionVoList=new ArrayList<>();
        QuestionExample questionExample=new QuestionExample();
        PaginationVo paginationVo=new PaginationVo();
        questionExample.createCriteria().andUserIdEqualTo(userId);
        int totalCount = questionMapper.countByExample(questionExample);
        int count=process(page,limit,totalCount);
        questionExample.setOrderByClause("id DESC");
        questionExample.setPage(count);
        questionExample.setLimit(limit);
        List<Question> questionList= questionMapper.selectByExample(questionExample);
        paginationVo.setPagination(totalCount, page, limit);
        return processVo(questionList, userMapper, questionVoList,paginationVo);
    }

    @Override
    public QuestionVo viewMyQuestion(Integer id) {
        QuestionVo questionVo=new QuestionVo();
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new MaLiException(ExceptionEnums.THIS_QUESTION_DOES_NOT_EXIST);
        }
        questionVo.setQuestion(question);
        User user = userMapper.selectByPrimaryKey(question.getUserId());
        questionVo.setUser(user);
        return questionVo;
    }

    @Override
    public void readingNumber(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        Integer viewCount =question.getViewCount();
        Lock lock=new ReentrantLock();
        lock.lock();
        if (viewCount==null){
            viewCount=0;
        }
        viewCount++;
        lock.unlock();
        question.setViewCount(viewCount);
        questionMapper.updateByPrimaryKey(question);
    }

    @Override
    public Question queryQuestionById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new MaLiException(ExceptionEnums.THIS_QUESTION_DOES_NOT_EXIST);
        }
        return question;
    }

    //静态方法区
    static Integer process(Integer page,Integer limit,Integer totalCount){
        Integer count;
        if (page<1){
            page=1;
        }
        if (page*limit>totalCount && totalCount%limit !=0){
            page=totalCount/limit +1;
        }
        count=limit*(page-1);
        return count;
    }
    static PaginationVo processVo(List<Question> questionList,UserMapper userMapper,
                                List<QuestionVo> questionVoList,
                                PaginationVo paginationVo){

        if (questionList.size()>0){
            for (Question question : questionList) {
                User user = userMapper.selectByPrimaryKey(question.getUserId());
                if (user==null){
                    return paginationVo;
                }
                QuestionVo questionVo =new QuestionVo();
                questionVo.setQuestion(question);
                questionVo.setUser(user);
                questionVoList.add(questionVo);
            }
            paginationVo.setQuestionVos(questionVoList);
        }
        return paginationVo;
    }
}
