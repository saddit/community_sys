package jmu.shijh.community_system.controller;

import jmu.shijh.community_system.common.annotation.MultiRequestBody;
import jmu.shijh.community_system.common.annotation.ParamCheck;
import jmu.shijh.community_system.common.exception.CustomException;
import jmu.shijh.community_system.common.util.*;
import jmu.shijh.community_system.domain.dto.MembersDTO;
import jmu.shijh.community_system.domain.entity.Members;
import jmu.shijh.community_system.domain.vo.MemberDetailVO;
import jmu.shijh.community_system.service.MemberService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/query/page")
    public JsonResp getMembers(@MultiRequestBody(required = false) MembersDTO dto, @MultiRequestBody(required = false) PageDTO pageInfo) {
        if (dto == null) {
            dto = new MembersDTO();
        }
        if (pageInfo == null || pageInfo.getPageNum() == null || pageInfo.getPageSize() == null) {
            pageInfo = new PageDTO();
        }
        PageVO res = memberService.getCommunityMembers(pageInfo, dto);
        return R.ok().data(res).build();
    }

    @RequestMapping("/query/detail/{id}")
    public JsonResp getMemberDetail(@PathVariable("id") Integer id) {
        MemberDetailVO memberDetail = memberService.getMemberDetail(id);
        return R.ok().data(memberDetail).build();
    }

    @RequestMapping("/add/batch")
    public JsonResp addMembersBatch(@RequestBody List<Members> members)  {
        memberService.addBatch(members);
        return R.ok().msg("添加成功").build();
    }

    @RequestMapping("/add/one")
    @ParamCheck(include = {"cid","mHouseNumber","mName","mPhone"})
    public JsonResp addMembers(@RequestBody(required = false) Members members)  {
        memberService.addBatch(Cl.list(members));
        return R.ok().msg("添加成功").build();
    }

    @RequestMapping("/update")
    @ParamCheck(include = "mId")
    public JsonResp updateMembers(@RequestBody(required = false) Members members)  {
        memberService.updateMember(members);
        return R.ok().msg("更新成功").build();
    }

    @RequestMapping("/delete/{id}")
    public JsonResp deleteMember(@PathVariable("id") Integer id) {
        memberService.deleteMember(new MembersDTO().setMId(id));
        return R.ok().msg("删除成功").build();
    }
}
