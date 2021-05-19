package jmu.shijh.community_system.domain.dto;

import com.alibaba.fastjson.JSON;
import jmu.shijh.community_system.common.exception.CustomException;
import lombok.Data;

@Data
public class WxQRReq {
    private String scene;
    private Integer width;
    private String page;

    public void setScene(Object o) {
        this.scene = JSON.toJSONString(o);
        if (scene.length() > 32) {
            throw new CustomException("scene字符串超过32字符,length(" + scene.length() + ")");
        }
    }
}
