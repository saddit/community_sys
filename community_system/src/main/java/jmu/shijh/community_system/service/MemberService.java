package jmu.shijh.community_system.service;

import jmu.shijh.community_system.common.util.PageDTO;
import jmu.shijh.community_system.common.util.PageVO;
import jmu.shijh.community_system.domain.dto.MembersDTO;
import jmu.shijh.community_system.domain.entity.Members;
import jmu.shijh.community_system.domain.vo.MemberDetailVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemberService {
    PageVO getCommunityMembers(PageDTO dto, Integer cid);

    MemberDetailVO getMemberDetail(Integer id);

    MemberDetailVO getMemberByPhone(String phone);

    PageVO getCommunityMembers(PageDTO page, MembersDTO dto);

    void addBatch(List<Members> list);

    void deleteMember(MembersDTO dto);

    void updateMember(Members members);
}
