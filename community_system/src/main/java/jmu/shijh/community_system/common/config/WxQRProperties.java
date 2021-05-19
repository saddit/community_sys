package jmu.shijh.community_system.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jmu.shijh.community_system.common.util.Str;
import jmu.shijh.community_system.common.util.Times;
import lombok.Data;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@ConfigurationProperties(prefix = "wx-program")
@Component
@Data
public class WxQRProperties {
    @Autowired
    RestTemplate restTemplate;

    private String url;
    private String token;
    private String appId;
    private String appSecret;
    private String page;
    private Integer width;

    private Date expiredTime;

    private String getAccessToken() {
        if (expiredTime != null && Times.sub(expiredTime, Times.now()).getTime() > Times.MIN) return token;
        String resp = restTemplate.getForObject(Str.f("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={}&secret={}", appId, appSecret), String.class);
        JSONObject j = JSON.parseObject(resp);
        token = (String) j.get("access_token");
        expiredTime = Times.now((Integer) j.get("expires_in"));
        return token;
    }

    public String getAccessUrl() {
        return url + "?access_token=" + getAccessToken();
    }
}
