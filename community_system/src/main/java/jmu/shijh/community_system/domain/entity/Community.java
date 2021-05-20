package jmu.shijh.community_system.domain.entity;

import java.io.Serializable;

import jmu.shijh.community_system.common.annotation.PrimaryField;
import jmu.shijh.community_system.common.annotation.UpdateField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * null
 * @TableName community
 */
@Data
@Accessors(chain = true)
public class Community implements Serializable {
    /**
     * 
     */
    @PrimaryField
    private Integer cId;

    /**
     * 负责人
     */
    @UpdateField
    private String cHead;

    /**
     * 社区名称
     */
    @UpdateField
    private String cName;

    /**
     * 省份
     */
    @UpdateField
    private String cProvince;

    /**
     * 城市
     */
    @UpdateField
    private String cCity;

    /**
     * 街道
     */
    @UpdateField
    private String cStreet;

    /**
     * 电话
     */
    @UpdateField
    private String cTel;

    private static final long serialVersionUID = 1L;
}