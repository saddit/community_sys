<template>
  <div>
    <el-form :model="memberData" label-width="auto" size="normal">
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="1"> 姓名 </el-col>
          <el-col :span="6" :offset="0">
            <el-input v-model="memberData.mName" placeholder="请输入姓名"/>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="1"> 联系电话 </el-col>
          <el-col :span="6" :offset="0">
            <el-input v-model="memberData.mPhone" placeholder="联系电话"/>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="1"> 性别 </el-col>
          <el-col :span="3" :offset="0">
            <el-radio-group v-model="memberData.mSex">
              <el-radio :label="'男'">
                男
              </el-radio>
              <el-radio :label="'女'">
                女
              </el-radio>
            </el-radio-group>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="1"> 年龄 </el-col>
          <el-col :span="3" :offset="0">
            <el-input v-model="memberData.mAge" placeholder="输入年龄" size="normal" clearable/>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="1"> 社区 </el-col>
          <el-col :span="3" :offset="0">
          <el-select
              v-model="memberData.cId"
              filterable
              remote
              placeholder="请输入关键词"
              :remote-method="getCommunities"
              :loading="loading">
              <el-option v-for="item in communities"
                :key="item.cId"
                :label="item.cName"
                :value="item.cId">
              </el-option> 
            </el-select>
          </el-col>        
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="1"> 楼房号 </el-col>
          <el-col :span="3" :offset="0">
            <el-input v-model="memberData.mHouseNumber" placeholder="请输入楼房号"/>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="1"> 是否为业主 </el-col>
          <el-col :span="3" :offset="0">
            <el-radio-group v-model="memberData.mIsHousehold">
              <el-radio :label="true">
                是
              </el-radio>
              <el-radio :label="false">
                否
              </el-radio>              
            </el-radio-group>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="1"> 工作单位 </el-col>
          <el-col :span="6" :offset="0">
            <el-input v-model="memberData.mWorkUnit" placeholder="请输入工作单位"/>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="1"> 车牌号 </el-col>
          <el-col :span="6" :offset="0">
            <el-input v-model="memberData.mCarNumber" placeholder="车牌号(闽D12322)"/>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
    <el-row :gutter="20">
      <el-col :span="6" :offset="6"><el-button type="primary" @click="sumbitmemberData(isUpdate)">提交</el-button></el-col>
    </el-row>
  </div>
</template>
<script>
import { ElMessage } from "element-plus";
export default {
  data() {
    return {
      memberData: {
        mId: "",
        mName: "",
        mPhone: "",
        mSex: "",
        mAge: "",
        mHouseNumber: "",
        mWorkUnit: "",
        mCarNumber: '',
        mIsHousehold: '',
        cName: '',
        cId: '',
      },
      communities:[],
      isUpdate: true,
      loading: false
    }
  },
  mounted() {
    this.getMemberData();
  },
  methods: {
    getMemberData() {
      if (this.$route.params.mId == undefined) {
        this.isUpdate = false;
        return;
      }
      this.memberData.mId = this.$route.params.mId
      this.$axios.post(
        "/members/query/detail/" + this.memberData.mId
      ) 
      .then(response=>{
        if(response.data.success) {
          this.memberData = response.data.data;
          this.communities = [{
            cName: this.memberData.cName,
            cId: this.memberData.cId
          }]
        } else {
          ElMessage.warning(response.data.message)
        }
      })
      .catch(error=>{
        ElMessage.error("系统错误")
      })
    },
    getCommunities(query) {
      if (query !== '') {
        this.loading = true;
        this.$axios.post("/community/query/page", {
          keyword: query
        })
        .then(response=>{
          if(response.data.success) {
            this.communities = response.data.data.list;
          } else {
            ElMessage.warning(response.data.message)
          }
          this.loading = false
        })
        .catch(error=>{
          ElMessage.error("系统错误")
        })        
      } else {
        this.communities = [];
      }
    },
    sumbitmemberData(isUpdate) {
      let api = '/members/add/one'
      if(isUpdate) {
        api = '/members/update'
      }
      this.$axios.post(api, this.memberData) 
      .then(response=>{
        if(response.data.success) {
          ElMessage.success(response.data.message)
          this.$router.push("/member");
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