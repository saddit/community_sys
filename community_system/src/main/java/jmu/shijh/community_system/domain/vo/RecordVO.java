package jmu.shijh.community_system.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import jmu.shijh.community_system.common.annotation.PrimaryField;
import jmu.shijh.community_system.common.annotation.UpdateField;
import lombok.Data;

import java.util.Date;

@Data
public class RecordVO {

    private Integer rId;

    private String mName;

    private String cName;

    private String location;

    /**
     * 是否离开城市
     */
    private Boolean rIsOutCity;

    /**
     * 是否来自境外
     */
    private Boolean rIsFromForeign;

    /**
     * 进入时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date rRecordTime;
}
