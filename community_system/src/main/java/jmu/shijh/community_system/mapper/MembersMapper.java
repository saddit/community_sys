package jmu.shijh.community_system.mapper;

import com.github.pagehelper.Page;
import jmu.shijh.community_system.common.sqlbuilder.*;
import jmu.shijh.community_system.common.sqlbuilder.enums.Logic;
import jmu.shijh.community_system.common.sqlbuilder.enums.Rule;
import jmu.shijh.community_system.domain.dto.MembersDTO;
import jmu.shijh.community_system.domain.entity.Members;
import jmu.shijh.community_system.domain.vo.MemberDetailVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity jmu.shijh.community_system.domain.entity.Members
 */
@Repository
public interface MembersMapper {

    @Select("select * from members where cId=#{arg0}")
    Page<Members> pagingQueryByCommunityId(Integer cid);

    @Select("select members.*, community.cTel, community.cName, community.cStreet " +
            "from members,community " +
            "where members.cId = community.cId and members.mId = #{arg0}")
    MemberDetailVO queryMemberDetailById(Integer id);

    @SelectProvider(value = provider.class, method = "query")
    Page<MemberDetailVO> queryByDTO(MembersDTO dto);

    @InsertProvider(value = provider.class, method = "insertBatch")
    int insertBatch(List<Members> memberList);

    @Delete("delete from members where cId=#{arg0}")
    int deleteByCommunityId(Integer cid);

    @Delete("delete from members where mid=#{arg0}")
    int deleteById(Integer mid);

    @InsertProvider(value = provider.class, method = "updateSelective")
    int updateSelectiveById(Members member);

    class provider {
        public String insertBatch(List<Members> list) {
            return new InsertSQL(list, "members").toString();
        }

        public String updateSelective(Members member) {
            return new UpdateSQL(member, "members").toString();
        }

        public String query(MembersDTO dto) {
            return new QuerySQL(dto, "members")
                    .select("members.*, c.cName, c.cStreet")
                    .join("community c on c.cId = members.cId")
                    .eqAll("mSex","mIsHousehold","mPhone")
                    .eq("members.cId", "cId")
                    .ge("mAge", "startAge")
                    .lt("mAge", "endAge")
                    .conditions(Condition.s()
                            .get(Logic.OR, "mName", Rule.REGEXP, "keyword")
                            .get(Logic.OR, "cName", Rule.REGEXP, "keyword")
                            .get(Logic.OR, "cStreet", Rule.REGEXP, "keyword")
                            .get(Logic.OR, "mHouseNumber", Rule.REGEXP, "keyword")
                            .toArray()).toString();
        }
    }
}




