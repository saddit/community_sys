package jmu.shijh.community_system.mapper;

import com.github.pagehelper.Page;
import jmu.shijh.community_system.common.sqlbuilder.Condition;
import jmu.shijh.community_system.common.sqlbuilder.ConditionSQL;
import jmu.shijh.community_system.common.sqlbuilder.InsertSQL;
import jmu.shijh.community_system.common.sqlbuilder.UpdateSQL;
import jmu.shijh.community_system.domain.entity.Community;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity jmu.shijh.community_system.domain.entity.Community
 */
@Repository
public interface CommunityMapper {

    @Select("select cId,cName,cProvince,cCity,cHead,cTel from community where deleted = 0")
    Page<Community> pagingQueryPart();

    @Select("select cId,cName,cProvince,cCity,cHead,cTel from community " +
            "where deleted = 0 and (cName regexp #{arg0} or cProvince regexp #{arg0} or cCity regexp #{arg0} or cTel regexp #{arg0} or cHead regexp #{arg0})")
    Page<Community> pagingQueryByKeyword(String keyword);

    @Select("select * from community where cId=#{arg0} and deleted = 0")
    Community queryDetailById(Integer id);

    @SelectProvider(value = provider.class, method = "queryByDTO")
    Community queryByDTO(Community community);

    @Select("select c.* from community c,members m where c.cId = m.cId and m.mId = #{arg0} and c.deleted = 0 and m.deleted = 0")
    Community queryDetailByMemberId(Integer id);

    @InsertProvider(value = provider.class, method = "insertBatch")
    int insertBatch(List<Community> list);

    @UpdateProvider(value = provider.class, method = "updateSelective")
    int updateSelectiveById(Community community);

    @Update("update community set deleted = 1 where cId=#{arg0}")
    int deleteById(Integer id);

    class provider {
        public String insertBatch(List<Community> list) {
            return new InsertSQL(list, "community").toString();
        }

        public String updateSelective(Community community) {
            return new UpdateSQL(community, "community").toString();
        }

        public String queryByDTO(Community community) {
            return new ConditionSQL(community, "community")
                    .select("*")
                    .eqVal("deleted", 0)
                    .cs(Condition.s()
                            .get("cName", "cName")
                            .get("cId", "cId")
                            .get("cHead", "cHead")
                    ).toString();
        }
    }
}




