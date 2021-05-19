<template>
  <div class="space-div">
    <el-card shadow="hover">
      <template v-for="(item, index) in items" :key="index">
        <el-row gutter="100">
          <el-col class="col-head" :class="item.icon" :span="5" :offset="7">
            {{ item.label }}
          </el-col>
          <el-col :span="6" :offset="0">
            <template v-if="Array.isArray(item.prop)">
              <template v-for="p in item.prop" :key="p">
                {{ data[p] }}
              </template>
            </template>
            <template v-else>
              {{ data[item.prop] }}
            </template>
          </el-col>
        </el-row>
      </template>
    </el-card>
  </div>
</template>
<script>
export default {
  name: 'common-card',
  props: {
    items: {
      type: Array,
      default: () => [],
    },
    data: {
      type: Array,
      default: () => [],
    },
  },
};
</script>
<style>
.space-div {
  width: 100%;
  height: fit-content;
  margin: 5% auto;
}
.space-div .el-row {
  margin: 10px auto;
  font-size: large;
}
.space-div .el-col {
  text-align: start;
  font-size: medium;
}
.col-head {
  font-weight: 550;
  /* border-right: 2px solid lightskyblue; */
  font-size: large;
  color: #aaa;
}
</style>