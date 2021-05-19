package jmu.shijh.community_system.common.util;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
public class PageVO {
    private List<?> list;
    private Integer pageNum;
    private Integer pageSize;
    private Long totalNum;
    private Integer totalPage;

    public PageVO(PageInfo<?> pageInfo) {
        list = pageInfo.getList();
        pageNum = pageInfo.getPageNum();
        pageSize = pageInfo.getPageSize();
        totalNum = pageInfo.getTotal();
        totalPage = pageInfo.getPages();
    }
}
