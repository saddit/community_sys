package jmu.shijh.community_system.domain.entity;

import lombok.Data;

@Data
public class WxQrResp {
    private Integer errcode;
    private String errmsg;
    private String contentType;
    private byte[] buffer;
}
