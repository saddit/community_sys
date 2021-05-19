<template lang="">
  <div>
    <el-table :data="data" border stripe fit>
      <template v-for="col in colList" :key="col">
        <el-table-column :prop="col.prop" :label="col.label" v-if="col.show">
        </el-table-column>
      </template>
      <el-table-column label="操作" width="210px" resizable="false"> 
        <template #default="scope">
          <el-button v-if="opEdit" @click="$emit('click-edit',scope.row)" type="primary" size="small">编辑</el-button>
          <el-popconfirm v-if="opDel" title="确定删除吗？" @confirm="$emit('click-delete',scope.row)">
            <template #reference>
              <el-button type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
           <el-button v-if="opLook" type="success" size="small" @click="$emit('click-look',scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-if="pagination"
      style="padding-top: 20px"
      background
      layout="prev, pager, next"
      @current-change="(index) => $emit('page-change', index)"
      :current-page="pageInfo.pageNum"
      :page-size="pageInfo.pageSize"
      :total="pageInfo.totalNum"
      >
    </el-pagination>
  </div>
</template>
<script>
export default {
  name: "common-table",
  props: {
    data: {
      type: Array,
      default: ()=>[]
    },
    colList: {
      type: Array,
      default: ()=>[]
    },
    pageInfo: {
      type: Object,
      default: ()=> {
        return {
          pageNum: 1,
          pageSize: 10,
          totalNum: 10,
        }
      }
    },
    opDel: {
      type:Boolean,
      default:()=> true
    },
    opLook: {
      type:Boolean,
      default: ()=> true
    },
    opEdit: {
      type:Boolean,
      default: ()=>true
    },
    pagination: {
      type: Boolean,
      default: ()=> false
    }
  },
  emits: ['click-eidt','click-delete', 'click-look', 'page-change']
};
</script>
<style lang="">
</style>