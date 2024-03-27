<template>
  <div style="padding: 10px">
    <el-row :gutter="10" style="text-align: left">
      <el-col :span="24">
        <el-card style="height: 100px">
          <div class="item1">
            <div>
              <span style="font-size: 17px;font-weight:bold;color: #E6A23C">{{ adopt.name }}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-button type="primary" plain size="mini" @click="handleJoin" v-if="adopt.state === '未领养'">领 养</el-button>
            </div>
            <div style="margin-top: 10px">
              <span style="color: #666666">时间</span>&nbsp;&nbsp;:&nbsp;&nbsp;<span style="color: #222">{{adopt.time }}</span>&nbsp;&nbsp;&nbsp;&nbsp;
              领养状态：&nbsp;&nbsp;<span style="color: #222" v-if="adopt.state === '未领养'" ><el-tag>{{adopt.state }}</el-tag></span>
              <span v-if="adopt.state === '已领养'" style="color: #222" ><el-tag type="danger">{{adopt.state }}</el-tag></span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 15px">
      <div style="padding: 10px 0; border-bottom: 1px solid #ccc; color: #1E90FF; font-size: 20px">
        内容
      </div>

      <div style="margin-top: 15px" v-html="adopt.content"></div>
    </el-card>

    <el-dialog title="信息" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
      <el-form label-width="140px" size="small" style="width: 95%;" :model="form">
        <el-form-item prop="description" label="申请说明">
          <el-input type="textarea" v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="join">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "BlogDetail",
  data() {
    return {
      id: this.$route.query.id,
      adopt: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      dialogFormVisible: false,
      form: {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleJoin() {
      this.dialogFormVisible = true
    },
    join() {
      let data = {
        name: this.adopt.name,
        description: this.form.description,
        user: this.user.username,
        adoptId: this.adopt.id,
        state: '待审核',
      }
      this.request.post("/adoptApply", data).then(res => {
        if (res.code === '200') {
          this.$message.success("申请领养，请等待审核")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
        this.form = {}
      })
    },
    load() {
      this.request.get("/adopt/" + this.id).then(res => {
        this.adopt = res.data
      })
    },
  }
}
</script>

<style scoped>
.item1 {
  color: #666;
}
</style>