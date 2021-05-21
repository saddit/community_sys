package jmu.shijh.community_system.controller;

import jmu.shijh.community_system.common.annotation.MultiRequestBody;
import jmu.shijh.community_system.common.annotation.ParamCheck;
import jmu.shijh.community_system.common.exception.CustomException;
import jmu.shijh.community_system.common.util.*;
import jmu.shijh.community_system.domain.dto.MembersDTO;
import jmu.shijh.community_system.domain.dto.RecordDTO;
import jmu.shijh.community_system.domain.entity.Community;
import jmu.shijh.community_system.domain.entity.Record;
import jmu.shijh.community_system.domain.vo.MemberDetailVO;
import jmu.shijh.community_system.service.CommunityService;
import jmu.shijh.community_system.service.MemberService;
import jmu.shijh.community_system.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CommunityService communityService;

    @PostMapping("/submit")
    @ParamCheck(include = {"mPhone", "cId"})
    public JsonResp submitRecord(@RequestBody(required = false) RecordDTO record) {
        MemberDetailVO member = memberService.getMemberByPhone(record.getMPhone());
        Community community = communityService.getCommunity(record.getCId());
        if (member == null || community == null) {
            throw new CustomException("用户或社区不存在");
        }
        Record records = new Record();
        records.setCId(record.getCId());
        records.setMId(member.getMId());
        records.setRIsHousehold( member.getCId().equals(record.getCId()) ? member.getMIsHousehold() : false);
        // test
        records.setRIsFromForeign(true);
        records.setRIsOutCity(true);
        return addNewRecord(records);
    }

    @RequestMapping("/query/page")
    public JsonResp getRecords(@MultiRequestBody(required = false) PageDTO pageInfo, @MultiRequestBody(required = false) RecordDTO dto) {
        if (dto == null) {
            dto = new RecordDTO();
        }
        if (pageInfo == null || pageInfo.getPageNum() == null || pageInfo.getPageSize() == null) {
            pageInfo = new PageDTO();
        }
        PageVO res = recordService.getRecord(pageInfo, dto);
        return R.ok().data(res).build();
    }

    @GetMapping("/delete/{id}")
    public JsonResp delete(@PathVariable("id") Integer rid) {
        recordService.deleteRecord(new RecordDTO().setRId(rid));
        return R.ok().build();
    }

    @RequestMapping("/members/page")
    public JsonResp getRelativeMember(@MultiRequestBody(required = false) PageDTO pageInfo, @MultiRequestBody(required = false) RecordDTO dto) {
        if (dto == null) {
            dto = new RecordDTO();
        }
        if (pageInfo == null || pageInfo.getPageNum() == null || pageInfo.getPageSize() == null) {
            pageInfo = new PageDTO();
        }
        PageVO res = recordService.getRelativeMembers(pageInfo, dto);
        return R.ok().data(res).build();
    }

    @RequestMapping("/add/one")
    @ParamCheck(include = {"mId", "cId", "rIsFromForeign", "isOutCity" })
    public JsonResp addNewRecord(@RequestBody Record record) {
        record.setRRecordTime(Times.now());
        recordService.addNewRecord(record);
        return R.ok().msg("记录成功").build();
    }

}
