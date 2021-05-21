package jmu.shijh.community_system.domain.dto;

import jmu.shijh.community_system.common.annotation.UpdateField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RecordDTO {
    /**
     * 用户id
     */
    private Integer mId;
    private Integer rId;
    /**
     * 社区id
     */
    private Integer cId;

    private String mPhone;
    private String mName;

    /**
     * 是否离开城市
     */
    private Boolean rIsOutCity;

    /**
     * 是否来自境外
     */
    private Boolean rIsFromForeign;

    private Date startDate;
    private Date endDate;

    private String keyword;

}
