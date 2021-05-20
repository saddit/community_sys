package jmu.shijh.community_system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jmu.shijh.community_system.common.config.WxQRProperties;
import jmu.shijh.community_system.common.exception.CustomException;
import jmu.shijh.community_system.common.util.Cl;
import jmu.shijh.community_system.common.util.PageDTO;
import jmu.shijh.community_system.common.util.PageVO;
import jmu.shijh.community_system.common.util.Str;
import jmu.shijh.community_system.domain.dto.WxQRReq;
import jmu.shijh.community_system.domain.entity.Community;
import jmu.shijh.community_system.mapper.CommunityMapper;
import jmu.shijh.community_system.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WxQRProperties qrProperties;

    @Override
    public BufferedImage getWxQRCode(Integer cid) throws IOException {
        WxQRReq req = new WxQRReq();
        req.setWidth(qrProperties.getWidth());
        req.setScene(cid);
        req.setPage(qrProperties.getPage());
        byte[] b = restTemplate.postForObject(qrProperties.getAccessUrl(), req, byte[].class);
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        return ImageIO.read(bis);
    }

    @Override
    public PageVO getCommunityPage(PageDTO pageDTO, String keyword) {
        PageHelper.startPage(pageDTO);
        Page<Community> resPage;
        if (Str.empty(keyword)) {
            resPage = communityMapper.pagingQueryPart();
        } else {
            resPage = communityMapper.pagingQueryByKeyword(keyword);
        }
        return new PageVO(resPage.toPageInfo());
    }

    @Override
    public Community getCommunity(Integer id) {
        return communityMapper.queryDetailById(id);
    }

    @Override
    @Transactional
    public void deleteOne(Integer id) {
        int i = communityMapper.deleteById(id);
        if (i == 0) {
            throw new CustomException("删除失败");
        }
    }

    @Override
    @Transactional
    public void updateById(Community community) {
        int i = communityMapper.updateSelectiveById(community);
        if (i == 0) {
            throw new CustomException("更新失败");
        }
    }

    @Override
    @Transactional
    public void addOne(Community community) {
        Community exits = communityMapper.queryByDTO(new Community().setCName(community.getCName()));
        if(exits != null) throw new CustomException("社区名重复");
        int i = communityMapper.insertBatch(Cl.list(community));
        if (i == 0) {
            throw new CustomException("添加失败");
        }
    }

    @Transactional
    public void addBatch(List<Community> communities) {
        int i = communityMapper.insertBatch(communities);
        if (i != communities.size()) {
            throw new CustomException("添加失败");
        }
    }
}
