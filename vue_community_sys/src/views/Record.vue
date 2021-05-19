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
        :items="cardItems"
      />
    </span>
    <template #footer>
      <el-button @click="showDetail = false">关闭</el-button>
    </template>
  </el-dialog>
</div>
<div style="width: 80%; margin: 0 auto">
    <div style="margin-bottom: 25px">
      <el-row :gutter="50">
        <el-col :span="3" :offset="-1">
          <el-date-picker
            v-model="dto.startDate"
            type="datetime"
            placeholder="选择起始日期"
            align="right"
            :shortcuts="shortcuts">
          </el-date-picker>
        </el-col>
        <el-col :span="3" :offset="2">
          <el-date-picker
            v-model="dto.endDate"
            type="datetime"
            placeholder="选择截至日期"
            align="right"
            :shortcuts="shortcuts">
          </el-date-picker>
        </el-col>
        <el-col :span="1" :offset="2" style="padding-top: 12px">
          <el-checkbox v-model="dto.rIsFromForeign" true-label="true" :false-label="null">跨国</el-checkbox>       
        </el-col>
        <el-col :span="1" :offset="1" style="padding-top: 12px">
          <el-checkbox v-model="dto.rIsOutCity" true-label="true" :false-label="null">跨市</el-checkbox>       
        </el-col>
        <el-col :span="6" :offset="3">
          <el-input
            v-model="dto.keyword"
            placeholder="关键字搜索"
            size="normal"
            clearable
          />
        </el-col>
        <el-col :span="2" :offset="0">
          <el-button type="primary" size="default" @click="getRecords(this.pageInfo.pageNum)">搜索</el-button>
        </el-col>
      </el-row>
    </div>
    <common-table
      :data="recordList"
      :colList="colList"
      :pagination="true"
      :pageInfo="pageInfo"
      :opEdit="false"
      @page-change="getRecords"
      @click-delete="deleteRecord"
      @click-look="toRecordDetail"
    />
  </div>
</template>
<script>
import { ElMessage } from "element-plus";
import CommonTable from '../components/CommonTable.vue'
import CommonCard from '../components/CommonCard.vue'
import {changeBoolToCN} from '../js/api'
export default {
  data() {
    return {
      detailData: {},
      showDetail: false,
      pageInfo: {
        pageSize: 10,
        pageNum: 1,
        totalNum: 1,
        orderBy: null,
      },
      dto:{
        mId: null,
        cId: null,
        rIsFromForeign: null,
        rIsOutCity: null,
        startDate: null,
        endDate: null,
        keyword: null,
      },
      recordList:[],
      colList:
      [
        {
          label: "rId",
          prop: "rId",
          show: false
        },
        {
          label: "社区名",
          prop: "cName",
          show: true
        },
        {
          label: "用户名",
          prop: "mName",
          show: true
        },
        {
          label: "是否跨国",
          prop: "rIsFromForeign",
          show: true
        },
        {
          label: "是否跨城",
          prop: "rIsOutCity",
          show: true
        },
        {
          label: "记录时间",
          prop: "rRecordTime",
          show: true
        }
      ],
      shortcuts: 
      [
        {
          text: '今天',
          value: new Date(),
        }, 
        {
          text: '昨天',
          value: (() => {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            return date
          })(),
        }, 
        {
          text: '一周前',
          value: (() => {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            return date
          })(),
        }
      ],
      cardItems: [
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
      ]
    }
  },
  mounted() {
    this.getRecords(1);
  },
  components:{
    CommonTable,
    CommonCard
  },
  methods:{ 
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
          ElMessage.error("系统错误")
        });
    },
    toRecordDetail(row) {
      this.detailData = row;
      this.showDetail = true;
    },
    getRecords(index) {
      this.pageInfo.pageNum = index; 
      this.$axios.post("/record/query/page",{
        pageInfo: this.pageInfo,
        dto: this.dto
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
        ElMessage.error("系统错误")
      })
    }
  }
}
</script>
<style lang="">
  
</style>