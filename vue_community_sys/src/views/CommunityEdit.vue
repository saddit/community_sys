<template>
  <div>
    <el-form :model="communityData" label-width="auto" size="normal">
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="0"> 社区名 </el-col>
          <el-col :span="8" :offset="0">
            <el-input v-model="communityData.cName" placeholder="请输入社区名"/>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="0"> 联系电话 </el-col>
          <el-col :span="8" :offset="0">
            <el-input v-model="communityData.cTel" placeholder="联系电话"/>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="0"> 负责人 </el-col>
          <el-col :span="6" :offset="0">
            <el-input v-model="communityData.cHead" placeholder="请输入负责人姓名"/>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="0"> 省市 </el-col>
          <el-col :span="3" :offset="0">
            <el-select v-model="communityData.cProvince" placeholder="请选择省份" clearable filterable @change="onProvinceChange">
              <el-option v-for="item in provinceOptions"
                :key="item.value"
                :label="item.label"
                :value="item.label">
              </el-option>
            </el-select>         
          </el-col>
          <el-col :span="1" :offset="0"> 省 </el-col>
          <el-col :span="3" :offset="0">
            <el-select v-model="communityData.cCity" placeholder="请选择城市" clearable filterable>
              <el-option v-for="item in cityOptions"
                :key="item.value"
                :label="item.label"
                :value="item.label">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="1" :offset="0"> 市 </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="6" :offset="0"> 详细街道 </el-col>
          <el-col :span="8" :offset="0">
            <el-input v-model="communityData.cStreet" placeholder="XXX(县/市)XXX街道"/>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
    <el-row :gutter="20">
      <el-col :span="6" :offset="6"><el-button type="primary" @click="sumbitCommunityData(isUpdate)">提交</el-button></el-col>
    </el-row>
  </div>
</template>
<script>
import { ElMessage } from "element-plus";
import {provinceDataList, cityDataMap, textCodeMap} from '../js/area-data'

export default {
  data() {
    return {
      communityData: {
        cName: "",
        cTel: "",
        cProvince: "",
        cCity: "",
        cStreet: "",
        cHead: "",
        cId: "",
      },
      provinceOptions: provinceDataList,
      cityOptions: [],
      isUpdate: true
    };
  },
  mounted() {
    this.getCommunityData();
  },
  methods: {
    onProvinceChange(item) {
      this.communityData.cCity = '';
      if(item == undefined || item == null || item == "") {
        this.cityOptions = [];
        return;
      }
      let code = textCodeMap.get(item);
      this.cityOptions = cityDataMap.get(code);
    },
    getCommunityData() {
      if (this.$route.params.id == undefined) {
        this.isUpdate = false;
        return;
      }
      this.communityData.cId = this.$route.params.id;
      this.$axios
        .post("/community/query/detail/"+this.communityData.cId)
        .then((response) => {
          if (response.data.success) {
            this.communityData = response.data.data;
            let _city = this.communityData.cCity;
            this.onProvinceChange(this.communityData.cProvince);
            this.communityData.cCity = _city;
          } else {
            ElMessage.warning(response.data.message);
          }
        })
        .catch(()=>{
          ElMessage.error("系统错误")
        })
    },
    sumbitCommunityData(isUpdate) {
      let api = '/community/add/one'
      if(isUpdate) {
        api = '/community/update'
      }
      this.$axios
        .post(api, this.communityData)
        .then((response) => {
          if (response.data.success) {
            ElMessage.sussess(response.data.message);
            this.$router.push("/community");
          } else {
            ElMessage.warning(response.data.message);
          }
        })
        .catch(()=>{
          ElMessage.error("系统错误")
        })      
    },
  },
};
</script>
<style>
</style>