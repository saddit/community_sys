<template>
  <div style="width: 80%; margin: 0 auto">
    <div style="margin-bottom: 25px">
      <el-row :gutter="5">
        <el-col :span="1" :offset="0"><el-button @click="this.$router.push('/community-edit')">新增</el-button></el-col>
        <el-col :span="5" :offset="3">
          <el-select clearable v-model="pageInfo.orderBy" placeholder="请选择排序" @change="getCommunities(this.pageInfo.pageNum)">
            <el-option
              v-for="item in sortList"
              :key="item"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-col>
        <el-col :span="6" :offset="3">
          <el-input
            v-model="pageInfo.keyword"
            placeholder="关键字搜索"
            size="normal"
            clearable
          />
        </el-col>
        <el-col :span="2" :offset="0">
          <el-button type="primary" size="default" @click="getCommunities(this.pageInfo.pageNum)">搜索</el-button>
        </el-col>
      </el-row>
    </div>
    <common-table
      :data="communityList"
      :colList="colList"
      :pagination="true"
      :pageInfo="pageInfo"
      @page-change="getCommunities"
      @click-delete="deleteCommunity"
      @click-edit="toEditView"
      @click-look="toDetailView"
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
        keyword: null,
      },
      communityList: [],
      colList: [
        {
          prop: "cId",
          label: "id",
          show: false,
        },
        {
          prop: "cName",
          label: "社区名",
          show: true,
        },
        {
          prop: "cProvince",
          label: "省份",
          show: true,
        },
        {
          prop: "cCity",
          label: "城市",
          show: true,
        },
        {
          prop: "cHead",
          label: "负责人",
          show: true,
        },
        {
          prop: "cTel",
          label: "联系电话",
          show: true,
        },
      ],
      sortList: [
        {
          label: "社区名",
          value: "cName",
        },
        {
          label: "省份",
          value: "cProvince",
        },
      ],
      loading: false
    };
  },
  mounted() {
    this.getCommunities(1);
  },
  components:{
    CommonTable
  },
  methods: {
    toEditView(row) {
      this.$router.push({
        name: "CommunityEdit",
        params: {
          id: row.cId,
        }
      });
    },
    toDetailView(row) {
      this.$router.push({
        name: "CommunityDetail",
        params: {
          id: row.cId
        }
      })
    },
    getCommunities(index) {
      this.loading = true;
      this.pageInfo.pageNum = index;
      this.$axios
        .post("/community/query/page",
          this.pageInfo,
        )
        .then((resp) => {
          if (resp.data.success) {
            this.communityList = resp.data.data.list;
            this.pageInfo.totalNum = resp.data.data.totalNum;
            this.pageInfo.pageNum = resp.data.data.pageNum;
            this.pageInfo.pageSize = resp.data.data.pageSize;
            this.loading = false;
          } else {
            ElMessage.warning(resp.data.message);
          }
        })
        .catch(() => {
          ElMessage.error("系统错误")
        });
    },
    deleteCommunity(row) {
      this.$axios
        .post("/community/delete/" + row.cId)
        .then((resp) => {
          if (resp.data.success) {
            ElMessage.success(resp.data.message);
            this.getCommunities(this.pageInfo.pageNum);
          } else {
            ElMessage.warning(resp.data.message);
          }
        })
        .catch(() => {
          ElMessage.error("系统错误")
        });
    }
  },
};
</script>
<style>
</style>
