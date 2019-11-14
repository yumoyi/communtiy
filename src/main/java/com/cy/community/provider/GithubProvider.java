package com.cy.community.provider;

import com.alibaba.fastjson.JSON;
import com.cy.community.dto.AccessTokenDTO;
import com.cy.community.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * github登录
 * @author cy
 * @since 2019-09-24 16:25
 */
@Component
@Slf4j
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(Objects.nonNull(response.body())){
                String string = response.body().string();
                return string.split("&")[0].split("=")[1];
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(Objects.nonNull(response.body())){
                String string = response.body().string();
                GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
                return githubUser;
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
