package jmu.shijh.community_system.service;

import jmu.shijh.community_system.common.util.PageDTO;
import jmu.shijh.community_system.common.util.PageVO;
import jmu.shijh.community_system.domain.entity.Community;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface CommunityService {
    BufferedImage getWxQRCode(Integer cid) throws IOException;

    PageVO getCommunityPage(PageDTO pageDTO, String keyword);

    Community getCommunity(Integer id);

    void deleteOne(Integer id);

    void updateById(Community community);

    void addOne(Community community);

    void addBatch(List<Community> communities);
}
