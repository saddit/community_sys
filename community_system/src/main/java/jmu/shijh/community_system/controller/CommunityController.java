package jmu.shijh.community_system.controller;

import com.alibaba.fastjson.JSON;
import jmu.shijh.community_system.common.annotation.ParamCheck;
import jmu.shijh.community_system.common.util.*;
import jmu.shijh.community_system.domain.dto.MembersDTO;
import jmu.shijh.community_system.domain.entity.Community;
import jmu.shijh.community_system.service.CommunityService;
import jmu.shijh.community_system.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/community", produces = "application/json;charset=utf-8")
public class CommunityController {

    @Autowired
    private CommunityService communityService;
    @Autowired
    private MemberService memberService;

    @Deprecated
    @RequestMapping("/img/wx/qrcode")
    public void getQRCode(Integer cid, HttpServletResponse response) throws IOException {
        BufferedImage wxQRCode = communityService.getWxQRCode(cid);
        ImageIO.write(wxQRCode, "jpeg", response.getOutputStream());
    }

    @RequestMapping("/img/qrcode/{id}")
    public void getQR(@PathVariable("id") Integer id,
                      @RequestParam(value = "width",required = false) Integer width,
                      @RequestParam(value = "height", required = false) Integer height,
                      HttpServletResponse response) throws IOException {
        Community community = communityService.getCommunity(id);
        QRUtils.getQRCode(JSON.toJSONString(community), response.getOutputStream(), width, height);
    }

    @RequestMapping("/query/page")
    @ParamCheck(include = {"orderBy"}, regexp = "^[\\s]*[a-zA-Z_]+", empty = true)
    public JsonResp getCommunityPage(@RequestBody(required = false) PageDTO pageDTO) {
        if (pageDTO == null || pageDTO.getPageNum() == null || pageDTO.getPageSize() == null) {
            pageDTO = new PageDTO();
        }
        PageVO pageVO = communityService.getCommunityPage(pageDTO,pageDTO.getKeyword());
        return R.ok().data(pageVO).build();
    }

    @RequestMapping("/query/detail/{id}")
    @ParamCheck(value = "id是必须的")
    public JsonResp getDetailCommunity(@PathVariable("id") Integer cid) {
        Community community = communityService.getCommunity(cid);
        return R.ok().data(community).build();
    }

    @RequestMapping("/delete/{id}")
    public JsonResp deleteOne(@PathVariable("id") Integer cid) {
        communityService.deleteOne(cid);
        return R.ok().msg("删除成功").build();
    }

    @RequestMapping("/members/delete/all/{id}")
    public JsonResp deleteAllMembers(@PathVariable("id") Integer cid) {
        memberService.deleteMember(new MembersDTO().setCId(cid));
        return R.ok().msg("删除成功").build();
    }

    @RequestMapping("/update")
    @ParamCheck(value = "id是必须的", include = {"cId"})
    public JsonResp updateCommunity(@RequestBody(required = false) Community community) {
        communityService.updateById(community);
        return R.ok().msg("更新成功").build();
    }

    @RequestMapping("/add/one")
    @ParamCheck(value = "名字、地区和电话是必须的", exclude = {"cId","cStreet","cHead"})
    public JsonResp addOne(@RequestBody Community community) {
        communityService.addOne(community);
        return R.ok().msg("添加成功").build();
    }

    @RequestMapping("/add/batch")
    public JsonResp addBatch(@RequestBody List<Community> communities) {
        communityService.addBatch(communities);
        return R.ok().msg("添加成功").build();
    }
}
