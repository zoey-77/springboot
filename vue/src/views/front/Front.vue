<template>
  <div>
<!--    头部-->
    <div style="display: flex; height: 60px; line-height: 60px; border-bottom: 1px solid #eee">
      <div style="width: 300px; display: flex; padding-left: 30px">
        <div style="width: 50px">
          <img src="../../assets/logo.png" alt="" style="width: 40px; position: relative; top: 8px; right: 0">
        </div>
        <div style="flex: 1">欢迎来到校园流浪猫管理系统</div>
      </div>
      <div style="flex: 1">

        <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" router style="border: none; height: 100%">
          <el-menu-item index="/front/cat">校猫图鉴</el-menu-item>
          <el-menu-item index="/front/map">校猫地图</el-menu-item>
          <el-menu-item index="/front/activity">救助活动</el-menu-item>
          <el-menu-item index="/front/adopt">猫咪领养</el-menu-item>
          <el-menu-item index="/front/blog">交流论坛</el-menu-item>
          <el-menu-item index="/front/myActivityApply">我的活动报名</el-menu-item>
          <el-menu-item index="/front/MyAdoptApply">我的领养申请</el-menu-item>
          <el-menu-item index="/front/myBlog">我的论坛发布</el-menu-item>
          <el-menu-item index="/front/myCollect">我的收藏</el-menu-item>
          <el-menu-item index="/front/message">信息反馈</el-menu-item>
        </el-menu>
      </div>
      <div style="width: 200px; text-align: right; padding-right: 10px">
        <div v-if="!user.username" style="text-align: right; padding-right: 30px">
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown style="width: 150px; cursor: pointer; text-align: right">
            <div style="display: inline-block">
              <img :src="user.avatarUrl" alt=""
                   style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
              <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            </div>
            <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
              <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                <router-link to="/front/password"><div>修改密码</div></router-link>
              </el-dropdown-item>
              <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                <router-link to="/front/person"><div>个人信息</div></router-link>
              </el-dropdown-item>
              <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                <span style="text-decoration: none" @click="logout"><div>退出</div></span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>

    <div style="width: 1000px; margin: 5px auto">
      <router-view  @refreshUser="getUser" />
    </div>
  </div>
</template>

<script>
export default {
  name: "Front",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {

  },
  methods: {
    logout() {
      this.$store.commit("logout")
      this.$message.success("退出成功")
    },
    getUser() {
      let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
      if (username) {
        // 从后台获取User数据
        this.request.get("/user/username/" + username).then(res => {
          // 重新赋值后台的最新User数据
          this.user = res.data
        })
      }
    }
  }
}
</script>

<style>
.item{
  display: inline-block;
  width: 100px;

  text-align: center;
  cursor: pointer
}
.item a {
  color: 	#1E90FF;
}
.item:hover{
  background-color: 	LightPink;
}
</style>
