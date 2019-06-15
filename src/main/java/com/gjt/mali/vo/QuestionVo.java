package com.gjt.mali.vo;

import com.gjt.mali.pojo.Question;
import com.gjt.mali.pojo.User;

public class QuestionVo {
    private Question question;
    private User user;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
