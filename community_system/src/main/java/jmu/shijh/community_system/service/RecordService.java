package jmu.shijh.community_system.service;

import jmu.shijh.community_system.common.util.PageDTO;
import jmu.shijh.community_system.common.util.PageVO;
import jmu.shijh.community_system.domain.dto.RecordDTO;
import jmu.shijh.community_system.domain.entity.Record;

public interface RecordService {
    PageVO getRecord(PageDTO page, RecordDTO dto);

    PageVO getRelativeMembers(PageDTO page, RecordDTO dto);

    void addNewRecord(Record record);
}
