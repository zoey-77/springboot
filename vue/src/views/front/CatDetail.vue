<template>
  <div style="padding: 10px">
    <el-row :gutter="10">
      <el-col :span="24">
        <el-card style="height: 120px">
          <div class="item1">
            <div>
              <span style="font-size: 17px;font-weight:bold;color: #E6A23C">{{ cat.name }}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
            <div style="margin-top: 10px">
              <span style="color: #666666">毛色</span>&nbsp;&nbsp;:&nbsp;&nbsp;<span style="color: #222">{{cat.color }}</span>&nbsp;&nbsp;&nbsp;&nbsp;
              <span style="color: #666666">状态</span>&nbsp;&nbsp;:&nbsp;&nbsp;<span style="color: #222">{{cat.state }}</span>&nbsp;&nbsp;&nbsp;&nbsp;
              <span style="color: #666666">添加时间</span>&nbsp;&nbsp;:&nbsp;&nbsp;<span style="color: #222">{{cat.time }}</span>&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 15px">
      <div style="padding: 10px 0; border-bottom: 1px solid #ccc; color: #1E90FF; font-size: 20px">
        性格
      </div>

      <div style="margin-top: 15px">{{cat.characters}}</div>
    </el-card>

    <el-card style="margin-top: 15px">
      <div style="padding: 10px 0; border-bottom: 1px solid #ccc; color: #1E90FF; font-size: 20px">
        介绍
      </div>

      <div style="margin-top: 15px" v-html="cat.content"></div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "BlogDetail",
  data() {
    return {
      id: this.$route.query.id,
      cat: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/cat/" + this.id).then(res => {
        this.cat = res.data
      })
    },
  }
}
</script>

<style scoped>
.item1 {
  padding: 10px;
  color: #666;
}
</style>