<template lang="">
<div style="width: 80%; margin: 0 auto">
    <div style="margin-bottom: 25px">
      <el-row :gutter="50">
        <el-col :span="1" :offset="0"><el-button @click="this.$router.push('/member-edit')">新增</el-button></el-col>
        <el-col :span="3" :offset="2">
          <el-input v-model="dto.startAge" placeholder="最小年龄" size="normal" clearable></el-input>         
        </el-col>
        <el-col :span="3" :offset="0">
          <el-input v-model="dto.endAge" placeholder="最大年龄" size="normal" clearable></el-input>
        </el-col>
        <el-col :span="1" :offset="1" style="padding-top: 12px">
          <el-checkbox v-model="dto.mIsHousehold" true-label="true" :false-label="null">业主</el-checkbox>       
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
          <el-button type="primary" size="default" @click="getMembers(this.pageInfo.pageNum)">搜索</el-button>
        </el-col>
      </el-row>
    </div>
    <common-table
      :data="memberList"
      :colList="colList"
      :pagination="true"
      :pageInfo="pageInfo"
      @page-change="getMembers"
      @click-delete="deleteMember"
      @click-edit="toEditView"
      @click-look="toMemberDetail"
    />
  </div>
</template>
<script>
import { ElMessage } from "element-plus";
import CommonTable from '../components/CommonTable.vue';
export default {
  data() {
    return {
      pageInfo: {
        pageSize: 10,
        pageNum: 1,
        totalNum: 1,
        orderBy: null,
      },
      dto:{
        mName: '',
        mIsHousehold: null,
        startAge: '',
        endAge: '',
        mSex: '',
        keyword: '',
      },
      memberList:[],
      colList:
      [
        {
          label: "mId",
          prop: "mId",
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
          label: "性别",
          prop: "mSex",
          show: true
        },
        {
          label: "年龄",
          prop: "mAge",
          show: true
        },
        {
          label: "楼房号",
          prop: "mHouseNumber",
          show: true
        },
        {
          label: "联系电话",
          prop: "mPhone",
          show: true
        },
      ],
    }
  },
  mounted() {
    this.getMembers(1);
  },
  components:{
    CommonTable
  },
  methods:{
    toEditView(row) {
      this.$router.push({
        name: "MemberEdit",
        params:{
          mId: row.mId
        }
      })
    },    
    deleteMember(row) {
      this.$axios
        .post("/members/delete/" + row.mId)
          .then((resp) => {
          if (resp.data.success) {
            ElMessage.success(resp.data.message);
            this.getMembers(this.pageInfo.pageNum);
          } else {
            ElMessage.warning(resp.data.message);
          }
        })
        .catch(() => {
          ElMessage.error("系统错误")
        });
    },
    toMemberDetail(row) {
      this.$router.push({
        name: 'MemberDetail',
        params: {
          mId: row.mId
        }
      })
    },
    getMembers(index) {
      this.pageInfo.pageNum = index;
      this.$axios.post("/members/query/page",{
        pageInfo: this.pageInfo,
        dto: this.dto
      }) 
      .then(response=>{
        if(response.data.success) {
          this.memberList = response.data.data.list;
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