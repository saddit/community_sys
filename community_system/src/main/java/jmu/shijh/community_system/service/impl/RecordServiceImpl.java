package jmu.shijh.community_system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jmu.shijh.community_system.common.exception.CustomException;
import jmu.shijh.community_system.common.util.Cl;
import jmu.shijh.community_system.common.util.PageDTO;
import jmu.shijh.community_system.common.util.PageVO;
import jmu.shijh.community_system.domain.dto.RecordDTO;
import jmu.shijh.community_system.domain.entity.Members;
import jmu.shijh.community_system.domain.entity.Record;
import jmu.shijh.community_system.domain.vo.RecordVO;
import jmu.shijh.community_system.mapper.RecordMapper;
import jmu.shijh.community_system.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecordServiceImpl implements RecordService {


    @Autowired
    private RecordMapper recordMapper;

    @Override
    public void deleteRecord(RecordDTO dto) {
        int i = recordMapper.deleteByDTO(dto);
        if (i == 0) {
            throw new CustomException("删除失败");
        }
    }

    @Override
    public PageVO getRecord(PageDTO page, RecordDTO dto) {
        PageHelper.startPage(page);
        Page<RecordVO> records = recordMapper.queryByDTO(dto);
        return new PageVO(records.toPageInfo());
    }

    @Override
    public PageVO getRelativeMembers(PageDTO page, RecordDTO dto) {
        PageHelper.startPage(page);
        Page<Members> members = recordMapper.queryRelativeMembersByDTO(dto);
        return new PageVO(members.toPageInfo());
    }

    @Override
    @Transactional
    public void addNewRecord(Record record) {
        int i = recordMapper.insertRecord(Cl.list(record));
        if (i == 0) {
            throw new CustomException("添加失败");
        }
    }
}
