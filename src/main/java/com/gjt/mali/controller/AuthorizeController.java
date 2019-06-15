package com.gjt.mali.controller;

import com.gjt.mali.pojo.User;
import com.gjt.mali.pojo.UserExample;
import com.gjt.mali.service.HomeService;
import com.gjt.mali.supplier.GitHubSupplier;
import com.gjt.mali.vo.AccessTokenVo;
import com.gjt.mali.vo.GitHubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHubSupplier gitHubSupplier;
    @Autowired
    private AccessTokenVo accessTokenVo;
    @Autowired
    private HomeService homeService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        accessTokenVo.setCode(code);
        accessTokenVo.setState(state);
        System.out.println(accessTokenVo.toString());
        String accessToken = gitHubSupplier.getAccessToken(accessTokenVo);
        GitHubUser user = gitHubSupplier.getUser(accessToken);
        if (user != null){
            //登录成功
            User user1 = new User();
            user1.setName(user.getName());
            String token= UUID.randomUUID().toString();
            user1.setToken(token);
            user1.setAccountId(String.valueOf(user.getId()));
            user1.setGmtCreate(System.currentTimeMillis());
            user1.setGmtModified(user1.getGmtCreate());
            user1.setAvatarUrl(user.getAvatar_url());
            UserExample ue=new UserExample();
            ue.createCriteria().andAccountIdEqualTo(String.valueOf(user.getId()));
            List<User> userList = homeService.queryAllUser(ue);
            if (userList.size()<=0){
                int addUser = homeService.createUser(user1);
                if (addUser>0){
                    response.addCookie(new Cookie("token", token));
                    request.getSession().setAttribute("user", user1);
                }
                return "redirect:/";
            }
            response.addCookie(new Cookie("token", userList.get(0).getToken()));
            request.getSession().setAttribute("user", userList.get(0));
            return "redirect:/";
        }else {
            //登录失败
            return "redirect:/";
        }
    }
}
