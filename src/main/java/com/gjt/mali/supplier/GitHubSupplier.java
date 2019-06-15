package com.gjt.mali.supplier;

import com.alibaba.fastjson.JSON;
import com.gjt.mali.vo.AccessTokenVo;
import com.gjt.mali.vo.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubSupplier {
    public String getAccessToken(AccessTokenVo accessTokenVo){
    MediaType mediaType = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenVo));
    Request request = new Request.Builder()
            .url("https://github.com/login/oauth/access_token")
            .post(body)
            .build();
    try (Response response = client.newCall(request).execute()){
        String message = response.body().string();
        String[] split = message.split("&");
        String[] split1 = split[0].split("=");
        String token = split1[1];
        return token;
    } catch (Exception e) {
            e.printStackTrace();
        }
     return null;
    }

    public GitHubUser getUser(String access_token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+access_token)
                .build();
        Response response ;
        try {
            response = client.newCall(request).execute();
            String string= response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return null;
    }

}
