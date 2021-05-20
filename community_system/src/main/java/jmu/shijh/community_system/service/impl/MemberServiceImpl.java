package jmu.shijh.community_system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jmu.shijh.community_system.common.exception.CustomException;
import jmu.shijh.community_system.common.util.PageDTO;
import jmu.shijh.community_system.common.util.PageVO;
import jmu.shijh.community_system.domain.dto.MembersDTO;
import jmu.shijh.community_system.domain.entity.Members;
import jmu.shijh.community_system.domain.vo.MemberDetailVO;
import jmu.shijh.community_system.mapper.MembersMapper;
import jmu.shijh.community_system.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.server.RMIClassLoader;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MembersMapper membersMapper;

    @Override
    public PageVO getCommunityMembers(PageDTO dto, Integer cid) {
        PageHelper.startPage(dto);
        Page<Members> members = membersMapper.pagingQueryByCommunityId(cid);
        return new PageVO(members.toPageInfo());
    }

    @Override
    public MemberDetailVO getMemberDetail(Integer id) {
        return membersMapper.queryMemberDetailById(id);
    }

    @Override
    public MemberDetailVO getMemberByPhone(String phone) {
        PageHelper.startPage(1,1);
        Page<MemberDetailVO> page = membersMapper.queryByDTO(new MembersDTO().setMPhone(phone));
        if (page.getResult().isEmpty()) {
            throw new CustomException("人员查找失败，该手机号不存在");
        }
        return page.getResult().get(0);
    }

    @Override
    public PageVO getCommunityMembers(PageDTO page, MembersDTO dto) {
        PageHelper.startPage(page);
        Page<MemberDetailVO> members = membersMapper.queryByDTO(dto);
        return new PageVO(members.toPageInfo());
    }

    @Override
    @Transactional
    public void addBatch(List<Members> list) {
        if (list.size() == 1) {
            Members members = list.get(0);
            Page<MemberDetailVO> exists = membersMapper.queryByDTO(new MembersDTO().setMPhone(members.getMPhone()));
            if (exists.size() > 0) throw new CustomException("手机号重复");
        }
        int i = membersMapper.insertBatch(list);
        if (i<list.size()) {
            throw new CustomException("插入失败,手机号可能重复");
        }
    }

    @Override
    @Transactional
    public void deleteMember(MembersDTO dto) {
        int i;
        if (dto.getCId() != null) {
            i = membersMapper.deleteByCommunityId(dto.getCId());
        }
        else if (dto.getMId() != null) {
            i = membersMapper.deleteById(dto.getMId());
        } else {
            throw new CustomException("参数不能为空");
        }
        if (i == 0) {
            throw new CustomException("删除失败");
        }
    }

    @Override
    @Transactional
    public void updateMember(Members members) {
        int i = membersMapper.updateSelectiveById(members);
        if (i == 0) {
            throw new CustomException("更新失败");
        }
    }
}
