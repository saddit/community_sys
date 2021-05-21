package jmu.shijh.community_system.mapper;

import com.github.pagehelper.Page;
import jmu.shijh.community_system.common.sqlbuilder.*;
import jmu.shijh.community_system.common.sqlbuilder.enums.Logic;
import jmu.shijh.community_system.common.sqlbuilder.enums.Rule;
import jmu.shijh.community_system.common.util.Cl;
import jmu.shijh.community_system.domain.dto.RecordDTO;
import jmu.shijh.community_system.domain.entity.Members;
import jmu.shijh.community_system.domain.entity.Record;
import jmu.shijh.community_system.domain.vo.RecordVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity jmu.shijh.community_system.domain.entity.Record
 */
@Repository
public interface RecordMapper {

    @SelectProvider(value = provider.class, method = "query")
    Page<RecordVO> queryByDTO(RecordDTO dto);

    @SelectProvider(value = provider.class, method = "queryMembers")
    Page<Members> queryRelativeMembersByDTO(RecordDTO dto);

    @InsertProvider(value = provider.class, method = "insertRecord")
    int insertRecord(List<Record> records);

    @UpdateProvider(value = provider.class,method = "deleteLogicDTO")
    int deleteByDTO(RecordDTO dto);

    class provider {
        private QuerySQL baseQuerySQL(RecordDTO dto) {
            return new QuerySQL(dto, "record")
                    .ge("rRecordTime", "startDate")
                    .lt("rRecordTime", "endDate")
                    .eqAll("rIsFromForeign", "rIsOutCity");
        }

        public String query(RecordDTO dto) {
            return baseQuerySQL(dto)
                    .select("record.*, c.cName, m.mName, concat(c.cProvince,c.cCity,c.cStreet) location")
                    .join("community c on c.cId=record.cId", "members m on m.mId = record.mId")
                    .eq("m.mId","mId")
                    .eq("c.cId", "cId")
                    .eqVal("record.deleted", 0)
                    .conditions(
                            Condition.get(Logic.OR, "mName", Rule.EQ, "keyword"),
                            Condition.get(Logic.OR, "mPhone", Rule.EQ, "keyword")
                    )
                    .toString();
        }

        public String deleteLogicDTO(RecordDTO dto) {
            return new LogicDeleteSQL(dto, "record")
                    .delete("deleted", 1)
                    .eqAll("mId","cId","rId").toString();
        }

        public String queryMembers(RecordDTO dto) {
            return baseQuerySQL(dto)
                    .select("m.*")
                    .join("members m on m.mId = record.mId")
                    .eq("m.mId","mId")
                    .toString();
        }

        public String insertRecord(List<Record> records) {
            return new InsertSQL(records, "record").toString();
        }
    }
}




