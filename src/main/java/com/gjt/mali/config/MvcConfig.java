package com.gjt.mali.config;

import com.gjt.mali.exception.ExceptionEnums;
import com.gjt.mali.exception.MaLiException;
import com.gjt.mali.pojo.User;
import com.gjt.mali.pojo.UserExample;
import com.gjt.mali.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private HomeService homeService;

    @Override
    //重写addVeiwControllers来简化页面的跳转
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/index", "/");
    }
    @Override
    //配置拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                Cookie[] cookies = request.getCookies();
                if (cookies==null){
                    throw new MaLiException(ExceptionEnums.USER_IS_NOT);
                }
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        String token = cookie.getValue();
                        UserExample userExample = new UserExample();
                        userExample.createCriteria().andTokenEqualTo(token);
                        List<User> userList = homeService.queryAllUser(userExample);
                        if (userList.size() > 0) {
                            request.getSession().setAttribute("user", userList.get(0));
                            break;
                        }
                    }
                }
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

            }
        }).addPathPatterns("/**").excludePathPatterns("../static/**");
    }


}
