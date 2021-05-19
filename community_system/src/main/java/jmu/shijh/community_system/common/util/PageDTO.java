package jmu.shijh.community_system.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class PageDTO {
    private Integer pageSize;
    private Integer pageNum;
    private String orderBy;
    private String keyword;
    private Boolean isDesc;

    public PageDTO() {
        pageNum = 1;
        pageSize = 10;
        orderBy = null;
        keyword = null;
        isDesc = false;
    }

    public String getOrderBy() {
        if (isDesc) {
           return orderBy + " desc";
        }
        return orderBy;
    }
}
