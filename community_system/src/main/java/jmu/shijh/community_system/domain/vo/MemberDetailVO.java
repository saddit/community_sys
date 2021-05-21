package jmu.shijh.community_system.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jmu.shijh.community_system.common.annotation.PrimaryField;
import jmu.shijh.community_system.common.annotation.UpdateField;
import lombok.Data;

@Data
public class MemberDetailVO {
    private Integer mId;
    /**
     * 用户姓名
     */
    private String mName;

    private Integer cId;

    /**
     * 手机号
     */
    private String mPhone;

    /**
     * 性别
     */
    private String mSex;

    /**
     * 年龄
     */
    private Integer mAge;

    /**
     * 楼房号
     */
    private String mHouseNumber;

    private String mHouse;

    /**
     * 工作单位
     */
    private String mWorkUnit;

    /**
     * 车牌号
     */
    @UpdateField
    private String mCarNumber;

    /**
     * 是否为业主 default 1
     */
    @UpdateField
    private Boolean mIsHousehold;

    /**
     * 社区名称
     */
    @UpdateField
    private String cName;

    /**
     * 街道
     */
    private String cStreet;

    /**
     * 电话
     */
    private String cTel;

    private static final long serialVersionUID = 1L;
}
