<template>
  <div style="width:1200px;margin-top: 15px">
    <el-card>
      <el-form  size="small" style="width: 100%">
        <div style="padding: 10px 0; margin: 0 0 0 0; border-bottom: 1px solid #ccc; color: #1E90FF; font-size: 20px">
          信息反馈
        </div>
        <div style="">
          <el-form-item>
            <el-input type="textarea" v-model="content" autocomplete="off" :rows="20"></el-input>
          </el-form-item>
        </div>
      </el-form>
      <div style="margin-top: 10px;text-align: right">
        <el-button size="small" type="success" @click="submit">提交</el-button>
      </div>
    </el-card>

    <div style="padding: 10px 0; margin: 10px 0 10px 0; border-bottom: 1px solid #ccc; color: orangered; font-size: 20px">
      大家在说什么
    </div>
    <div v-for="item in tableData" style="padding:5px 0">
      <el-card>
        <div style="display: flex">
<!--          <div style="width: 80px">-->
<!--            <el-avatar :size="50" :src="item.user.avatarUrl"></el-avatar>-->
<!--          </div>-->
          <div style="flex: 1">
            <div style="display: flex">
              {{ item.user }} <span style="margin-left: 10px">{{ item.time }}</span>
            </div>
            <div style="margin-top: 10px; color: #666">{{ item.name }}</div>
          </div>
        </div>
      </el-card>
    </div>
    <div style="padding: 10px 0;">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>

export default {
  name: "ShortComment",
  data() {
    return {
      content: '',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      types: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
    }
  },
  created() {
    this.load()
  },
  methods: {
    submit() {
      if (this.user.username == null){
        this.$message.warning("请登录")
        return;
      }
      let data = {
        name: this.content,
      }
      console.log(data);
      this.request.post("/message", data).then(res => {
        if (res.code === '200') {
          this.$message.success("发表成功")
          this.load()
          this.content = ''
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    load() {
      this.request.get("/message/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
  }
}
</script>

<style scoped>

</style>

