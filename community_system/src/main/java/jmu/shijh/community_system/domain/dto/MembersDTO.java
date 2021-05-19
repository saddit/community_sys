package jmu.shijh.community_system.domain.dto;

import jmu.shijh.community_system.common.annotation.PrimaryField;
import jmu.shijh.community_system.common.annotation.UpdateField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MembersDTO {
    private Integer mId;

    /**
     * 用户姓名
     */
    private String mName;

    private Integer cId;

    /**
     * 性别
     */
    private String mSex;
    private String mPhone;

    private Boolean mIsHousehold;

    private Integer startAge;
    private Integer endAge;
    private String keyword;
}
