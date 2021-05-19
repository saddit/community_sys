<template>
  <div id="home-div">
    <el-container :direction="vertical" style="height: 100%;">
      <el-header style="height: fit-content;">
        <el-page-header @back="goBack" id="page-header" :content="title"/>
      </el-header>
      <el-container :direction="horizontal" style="height: 100%">
        <el-aside width="150px">
          <el-menu mode="vertitcal" router >
            <el-menu-item index="/community">社区管理</el-menu-item>
            <el-menu-item index="/member">居民管理</el-menu-item>
            <el-menu-item index="/record">记录管理</el-menu-item>
          </el-menu>
        </el-aside>
        <el-container :direction="vertical">
          <el-main height="100%">
            <router-view/>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100vh;
  margin: 0;  
}
#home-div {
  height: 100%;
  margin: 0;
}
body {
  margin: 0;
}
#page-header {
  padding: 10px 0px;
}
.el-table th.gutter {
  display: table-cell !important;
}
</style>

<script>
  export default {
    data() {
      return {
        title: '首页',
        fromPath: '/',
      }
    },
    methods: {
      updateTitle(route) {
        this.title = route.meta.title;
      },
      goBack() {
        this.$router.push({path: this.fromPath})
      }
    },
    watch: {
      $route(to, from) {
        this.updateTitle(to);
        this.fromPath = from.path; 
      }
    }
  }
</script>
