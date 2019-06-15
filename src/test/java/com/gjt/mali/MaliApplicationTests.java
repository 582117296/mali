package com.gjt.mali;

import com.gjt.mali.mapper.QuestionMapper;
import com.gjt.mali.mapper.UserMapper;
import com.gjt.mali.pojo.Question;
import com.gjt.mali.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MapperScan("com.gjt.mali.mapper")
@SpringBootTest
public class MaliApplicationTests {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Test
    public void contextLoads() {

        Question question=new Question();
        question.setTitle("大是非得失");
        question.setTag("多少集,护甲时");
        question.setDescription("硕大的撒长度时彩打");
        question.setUserId(9);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModifiled(question.getGmtCreate());
        System.out.println(question.toString());
        int num=questionMapper.insert(question);
        if (num>0){
            System.out.println("成功!");

        }else {
            System.out.println("失败");
        }
    }

    @Test
    public void redisTest() {
        User user = userMapper.selectByPrimaryKey(2);
        RedisTemplate redisTemplate=new RedisTemplate();
         redisTemplate.boundSetOps(user);


    }
}
