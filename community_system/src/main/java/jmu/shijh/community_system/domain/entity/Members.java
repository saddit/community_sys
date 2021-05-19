package jmu.shijh.community_system.domain.entity;

import java.io.Serializable;

import jmu.shijh.community_system.common.annotation.PrimaryField;
import jmu.shijh.community_system.common.annotation.UpdateField;
import lombok.Data;

/**
 * null
 * @TableName members
 */
@Data
public class Members implements Serializable {

    @PrimaryField
    private Integer mId;

    /**
     * 用户姓名
     */
    @UpdateField
    private String mName;

    /**
     * 手机号
     */
    @UpdateField
    private String mPhone;

    /**
     * 性别
     */
    @UpdateField
    private String mSex;

    /**
     * 年龄
     */
    @UpdateField
    private Integer mAge;

    /**
     * 楼房号
     */
    @UpdateField
    private String mHouseNumber;

    /**
     * 工作单位
     */
    @UpdateField
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
     * 社区id
     */
    @UpdateField
    private Integer cId;

    private static final long serialVersionUID = 1L;
}