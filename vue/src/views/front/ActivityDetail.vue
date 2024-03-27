<template>
  <div style="padding: 10px">
    <el-row :gutter="10" style="text-align: left">
      <el-col :span="24">
        <el-card style="height: 100px">
          <div class="item1">
            <div>
              <span style="font-size: 17px;font-weight:bold;color: #E6A23C">{{ activity.name }}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-button type="primary" plain size="mini" @click="handleJoin">报名</el-button>
            </div>
            <div style="margin-top: 10px">
              <span style="color: #666666">时间</span>&nbsp;&nbsp;:&nbsp;&nbsp;<span style="color: #222">{{activity.time }}</span>&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 15px">
      <div style="padding: 10px 0; border-bottom: 1px solid #ccc; color: #1E90FF; font-size: 20px">
        活动内容
      </div>

      <div style="margin-top: 15px" v-html="activity.content"></div>
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
      activity: {},
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
        name: this.activity.name,
        description: this.form.description,
        user: this.user.username,
        activityId: this.activity.id,
        state: '待审核',
      }
      this.request.post("/activityApply", data).then(res => {
        if (res.code === '200') {
          this.$message.success("报名成功，请等待审核")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
        this.form = {}
      })
    },
    load() {
      this.request.get("/activity/" + this.id).then(res => {
        this.activity = res.data
      })
    },
    sign(){

    }
  }
}
</script>

<style scoped>
.item1 {
  color: #666;
}
</style>