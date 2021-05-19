<template lang="">
  <div style="width: 80%; margin: 0 auto">
    <common-card
    :data="communityData"
    :items="commonCardItems"
    />
    <h3>居民信息</h3>
    <div class="space-div">
      <common-table 
        :data="members"
        :colList="colList"
        pagination="true"
        :pageInfo="pageInfo"
        @page-change="getMembers"
        @click-look="toMemberDetail"
        @click-edit="toEditView"
        @click-delete="deleteMember"
      />
    </div>
    <el-image :src="getQRImg()" fit="fill" :lazy="true" :preview-src-list="[getQRImg(500,500)]" />
    
  </div>
</template>
<script>
import { ElMessage } from "element-plus";
import CommonTable from '../components/CommonTable.vue'
import CommonCard from '../components/CommonCard.vue'
import {baseUrl} from '../js/base'


export default {
  data() {
    return {
      commonCardItems:[
        {
          label: '社区名',
          prop: "cName",
          icon: "el-icon-house"
        },
        {
          label: '联系电话',
          prop: "cTel",
          icon: "el-icon-phone"
        },
        {
          label: '负责人',
          prop: "cHead",
          icon: "el-icon-s-custom"
        },
        {
          label: '省市',
          prop: ["cProvince","cCity"],
          icon: "el-icon-position"
        },
        {
          label: '详细街道',
          prop: "cStreet",
          icon: "el-icon-location-outline"
        },
      ],
      members:[],
      colList:
      [
        {
          label: "mId",
          prop: "mId",
          show: false
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
      communityData: {
        cName: "",
        cTel: "",
        cProvince: "",
        cCity: "",
        cStreet: "",
        cHead: "",
        cId: null,
      },
      pageInfo: {
        pageSize: 3,
        pageNum: 1,
        totalNum: 1,
        orderBy: null,
        keyword: null,
      },
    }
  },
  components: {
    CommonTable,
    CommonCard
  },
  mounted() {
    this.getCommunityData();
    this.getMembers(1);
  },
  methods: {
    getQRImg(width, height) {
      if(width == undefined || height == undefined) width = height = '';
      return baseUrl + "/community/img/qrcode/"+this.communityData.cId 
              +"?width=" + width + "&height=" + height + "&t=" + Date.now();
    },
    toEditView(row) {
      this.$router.push({
        name: "MemberEdit",
        params:{
          mId: row.mId
        }
      })
    },
    getCommunityData() {
      if (this.$route.params.id == undefined) {
        if (this.communityData.id == null) {return;}
      } else {
        this.communityData.cId = this.$route.params.id;
      }
      this.$axios
        .post("/community/query/detail/"+this.communityData.cId)
        .then((response) => {
          if (response.data.success) {
            this.communityData = response.data.data;
          } else {
            ElMessage.warning(response.data.message);
          }
        })
        .catch(()=>{
          ElMessage.error("系统错误")
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
      this.$axios
        .post("/members/query/page",{
          pageInfo: this.pageInfo,
          dto: {
            cId: this.communityData.cId
          }
        })
        .then((response) => {
          if (response.data.success) {
            this.members = response.data.data.list;
            this.pageInfo.totalNum = response.data.data.totalNum;
            this.pageInfo.pageNum = response.data.data.pageNum;
            this.pageInfo.pageSize = response.data.data.pageSize;
          } else {
            ElMessage.warning(response.data.message);
          }
        })
        .catch(()=>{
          ElMessage.error("系统错误")
        })
    }
  }
};
</script>
<style>
.el-image{
  position: absolute;
  top: 18%;
  right: 15%;
}
h3 {
  border: 1px solid #eee;
  border-radius: 10px;
  color: #777;
  padding-top: 8px;
  padding-bottom: 8px;
}
h3:hover {
	box-shadow: 1px 1px 10px #ddd;
}
</style>