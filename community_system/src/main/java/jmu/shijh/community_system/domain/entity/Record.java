package jmu.shijh.community_system.domain.entity;

import java.io.Serializable;
import java.util.Date;

import jmu.shijh.community_system.common.annotation.PrimaryField;
import jmu.shijh.community_system.common.annotation.UpdateField;
import lombok.Data;

/**
 * null
 * @TableName record
 */
@Data
public class Record implements Serializable {

    @PrimaryField
    private Integer rId;

    /**
     * 用户id
     */
    @UpdateField
    private Integer mId;

    /**
     * 社区id
     */
    @UpdateField
    private Integer cId;

    /**
     * 是否离开城市
     */
    @UpdateField
    private Boolean rIsOutCity;

    /**
     * 是否来自境外
     */
    @UpdateField
    private Boolean rIsFromForeign;

    /**
     * 是否业主
     */
    @UpdateField
    private Boolean rIsHousehold;

    /**
     * 进入时间
     */
    private Date rRecordTime;

    private static final long serialVersionUID = 1L;
}