<template>
<div>
  <el-dialog
    title="记录详情"
    v-model="showDetail"
    width="50%"
    >
    <span>
      <common-card
        :data="detailData"
        :items="dialogItems"
      />
    </span>
    <template #footer>
      <el-button @click="showDetail = false">关闭</el-button>
    </template>
  </el-dialog>
</div>
  <div style="width: 80%; margin: 0 auto;">
    <common-card
      :data="memberData"
      :items="cardItems"
     />
     <common-table
     :colList="tableCols"
     :data="recordList"
     :pageInfo="pageInfo"
     :pagination="true"
     :opEdit="false"
      @page-change="getMemberRecord"
      @click-delete="deleteRecord"
      @click-look="toRecordDetail"
     />
  </div>
</template>
<script>
import { ElMessage } from "element-plus";
import CommonCard from "../components/CommonCard.vue"
import CommonTable from '../components/CommonTable.vue'
import {changeBoolToCN} from '../js/api'


export default {
  data() {
    return {
      recordList:[],
      memberData: {
        mId: "1",
        mName: "张三",
        mPhone: "123134",
        mSex: "男",
        mAge: "28",
        mHouseNumber: "A1",
        mWorkUnit: "软件园二期",
        mCarNumber: "闽C66666",
        mIsHousehold: "1",
        cId: "1"
      },
      cardItems: [
        {
          label: "姓名",
          prop: "mName",
          icon: ""
        },
        {
          label: "性别",
          prop: "mSex",
          icon: ""
        },
        {
          label: "是否为业主",
          prop: "mIsHousehold",
          icon: ""
        },
        {
          label: "社区",
          prop: "cName",
          icon: ""
        },
        {
          label: "社区街道",
          prop: "cStreet",
          icon: ""
        },
        {
          label: "联系电话",
          prop: "mPhone",
          icon: ""
        },
        {
          label: "工作单位",
          prop: "mWorkUnit",
          icon: ""
        },
        {
          label: "车牌号",
          prop: "mCarNumber",
          icon: ""
        },
      ],
      tableCols: [
        {
          prop:'rId',
          show: false
        },
        {
          label: '社区',
          prop: 'cName',
          show: true,
        },
        {
          label: '地址',
          prop: 'location',
          show: true,
        },
        {
          label: '是否跨城',
          prop: 'rIsOutCity',
          show: true,
        },
        {
          label: '是否跨国',
          prop: 'rIsFromForeign',
          show: true,
        },
        {
          label: '记录时间',
          prop: 'rRecordTime',
          show: true,
        }
      ],
      dialogItems: [
        {
          label: "记录时间",
          prop: "rRecordTime"
        },
        {
          label: "人员姓名",
          prop: "mName",
        },
        {
          label: "通行社区",
          prop: "cName",
        },
        {
          label: "通行地址",
          prop: "location"
        },
        {
          label: "跨市",
          prop: "rIsOutCity"
        },
        {
          label: "跨国",
          prop: "rIsFromForeign"
        },
      ],
      pageInfo: {
        pageSize: 3,
        pageNum: 1,
        totalNum: 1,
        orderBy: null,
        keyword: null,
      },
      detailData: {},
      showDetail: false,
    }
  },
  mounted() {
    this.getMemberData();
    this.getMemberRecord(1);
  },
  methods: {
    deleteRecord(row) {
      this.$axios
        .post("/record/delete/" + row.rId)
          .then((resp) => {
          if (resp.data.success) {
            ElMessage.success(resp.data.message);
            this.getRecords(this.pageInfo.pageNum);
          } else {
            ElMessage.warning(resp.data.message);
          }
        })
        .catch(() => {
          ElMessage.error("系统错误1")
        });
    },
    toRecordDetail(row) {
      this.detailData = row;
      this.showDetail = true;
    },
    getMemberData() {
      this.memberData.mId = this.$route.params.mId
      this.$axios.post(
        "/members/query/detail/" + this.memberData.mId
      ) 
      .then(response=>{
        if(response.data.success) {
          this.memberData = response.data.data;
          changeBoolToCN([this.memberData], ["mIsHousehold"])
        } else {
          ElMessage.warning(response.data.message)
        }
      })
      .catch(error=>{
        ElMessage.error("系统错误2")
      })
    },
    getMemberRecord(index) {
      this.pageInfo.pageNum = index; 
      this.$axios.post("/record/query/page",{
        pageInfo: this.pageInfo,
        dto: {
          mId: this.memberData.mId
        }
      })
      .then(response=>{
        if(response.data.success) {
          this.recordList = response.data.data.list;
          this.pageInfo.totalNum = response.data.data.totalNum;
          this.pageInfo.pageNum = response.data.data.pageNum;
          this.pageInfo.pageSize = response.data.data.pageSize;
          changeBoolToCN(this.recordList, ["rIsFromForeign","rIsOutCity"])
        } else {
          ElMessage.warning(response.data.message)
        }
      })
      .catch(error=>{
        ElMessage.error("系统错误3")
      })
    }
  },
  components: {
    CommonCard,
    CommonTable,
  },
};
</script>
<style>
</style>
