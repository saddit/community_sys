package jmu.shijh.community_system.mapper;

import com.github.pagehelper.Page;
import jmu.shijh.community_system.common.sqlbuilder.InsertSQL;
import jmu.shijh.community_system.common.sqlbuilder.UpdateSQL;
import jmu.shijh.community_system.domain.entity.Community;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity jmu.shijh.community_system.domain.entity.Community
 */
@Repository
public interface CommunityMapper {

    @Select("select cId,cName,cProvince,cCity,cHead,cTel from community")
    Page<Community> pagingQueryPart();

    @Select("select cId,cName,cProvince,cCity,cHead,cTel from community " +
            "where cName regexp #{arg0} or cProvince regexp #{arg0} or cCity regexp #{arg0} or cTel regexp #{arg0} or cHead regexp #{arg0}")
    Page<Community> pagingQueryByKeyword(String keyword);

    @Select("select * from community where cId=#{arg0}")
    Community queryDetailById(Integer id);

    @Select("select c.* from community c,members m where c.cId = m.cId and m.mId = #{arg0}")
    Community queryDetailByMemberId(Integer id);

    @InsertProvider(value = provider.class, method = "insertBatch")
    int insertBatch(List<Community> list);

    @UpdateProvider(value = provider.class, method = "updateSelective")
    int updateSelectiveById(Community community);

    @Delete("delete from community where cId=#{arg0}")
    int deleteById(Integer id);

    class provider {
        public String insertBatch(List<Community> list) {
            return new InsertSQL(list,"community").toString();
        }
        public String updateSelective(Community community) {
            return new UpdateSQL(community, "community").toString();
        }
    }
}




