<template>
  <div style="padding: 10px">
    <el-row :gutter="10">
      <el-col :span="24">
        <el-card style="height: 120px">
          <div class="item1">
            <div>
              <span style="font-size: 17px;font-weight:bold;color: #E6A23C">{{ blog.name }}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <span><img src="../../assets/私信.png" alt="" style="width: 18px; "  @click="$router.push('/front/talk')" /></span>&nbsp;&nbsp;&nbsp;
              <span v-if="state" ><img src="../../assets/喜欢.png" alt="" style="width: 18px; "  @click="collect" /></span>
              <span v-if="!state" ><img src="../../assets/喜欢 (1).png" alt="" style="width: 18px;"  @click="collect" /></span>
            </div>
            <div style="margin-top: 10px">
              <span style="color: #666666">发布人</span>&nbsp;&nbsp;:&nbsp;&nbsp;<span style="color: #222">{{blog.user }}</span>&nbsp;&nbsp;&nbsp;&nbsp;
              <span style="color: #666666">发布时间</span>&nbsp;&nbsp;:&nbsp;&nbsp;<span style="color: #222">{{blog.time }}</span>&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>


    <el-card style="margin-top: 15px">
      <div style="padding: 10px 0; border-bottom: 1px solid #ccc; color: #1E90FF; font-size: 20px">
        博客内容
      </div>

      <div style="margin-top: 15px" v-html="blog.content"></div>
    </el-card>

    <el-card style="margin-top: 15px">
      <div style="width: 1160px; margin: 0 auto">
        <div style="padding: 10px 0;  border-bottom: 1px solid #ccc; color: #1E90FF; font-size: 20px;width: 81%">
          评论
        </div>
        <div style="margin: 20px 0">
          <div style="margin: 10px 0">
            <el-input type="textarea" placeholder="请输入留言" v-model="comment.content" style="width: 81%"></el-input>
            <div style="text-align: right; margin: 10px 0">
              <el-button style="margin: 10px 220px 0 0" type="primary" @click="submit2">提交</el-button>
            </div>
          </div>
        </div>

        <div style="margin: 20px 0">
          <div style="margin: 10px 0; font-size: 24px; padding: 10px 0; border-bottom: 1px solid #ccc; text-align: left">
            评论列表
          </div>

          <div style="margin: 20px 0;  text-align: left;">
            <div style="padding: 10px 0; " v-for="item in comments" :key="item.id">
              <div style="display: flex">
                <div style="width: 80px">
                  <el-avatar :size="50" :src="item.img"></el-avatar>
                </div>
                <div style="flex: 1">
                  <div style="display: flex">
                    {{ item.username }} <span style="margin-left: 10px">{{ item.time }}</span>
                  </div>
                  <div style="margin-top: 10px; color: #666">{{ item.content }}</div>
                  <div>
                    <el-button type="text" @click="reply(item.id, item.username)">回复</el-button>
                  </div>

                  <!-- 回复列表 -->
                  <div v-if="item.children.length"
                       style="margin-left: 50px; width: 72%;background-color: aliceblue; padding: 10px; border-radius: 10px">
                    <div v-for="sub in item.children" :key="sub.id">
                      <div style="padding:5px 0">
                        <div style="width: 100px;display: flex;align-items: center">
                          <el-avatar :size="40" :src="sub.img"></el-avatar>&nbsp;
                          <span style="cursor: pointer;font-weight:bold" @click="reply(sub.pid, sub.userId)">{{
                              sub.username
                            }}</span>
                        </div>
                        <br>
                        <span style="margin-left: 47px">
                        回复 <span style="color: cornflowerblue">@{{ sub.parentName }}</span>
                      <span style="color: #666;width: 100%; margin-left: 10px">{{ sub.content }}</span>
                    </span>
                        <span style="float: right; font-size: 13px; color: #666; margin-top: 3px">{{ sub.time }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>

        <el-dialog title="回复" :visible.sync="dialogFormVisible3" width="50%">
          <el-form :model="replyComment">
            <el-form-item label="内容" >
              <el-input type="textarea" v-model="replyComment.content" style="width: 100%"></el-input>

            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible3 = false">取 消</el-button>
            <el-button type="primary" @click="saveReply">确 定</el-button>
          </div>
        </el-dialog>

      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "BlogDetail",
  data() {
    return {
      id: this.$route.query.id,
      blog: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      form: {},
      comments: [],
      comment: {
        content: ''
      },
      replyComment: {},
      dialogFormVisible3: false,
      dialogFormVisible4: false,
      state: true,
      pageviews: 0
    }
  },
  created() {
    this.request.get("/blog/pageviews/" + this.id).then(res => {
      if (res.code === '200') {
        this.pageviews = res.data.pageviews
      }
    })

    this.load()
    this.load2()
  },
  methods: {
    collect(){
      if (this.user.username == null){
        this.$message.warning("请登录")
        return;
      }

      let data = {foreignId:this.id,userId:this.user.id}
      this.request.post("/collect", data).then(res => {
        if (res.code === '200') {
          this.state = false;
          this.$message.success("点赞成功")
        } else {
          this.state = true;
          this.$message.error(res.msg)
        }
      })
    },
    load() {
      this.request.get("/blog/" + this.id).then(res => {
        this.blog = res.data
      })

      this.request.get("/collect/repeat/" + this.id + '/' + this.user.id).then(res => {
        if (res.code === '200') {
          this.state = true;
        } else {
          this.state = false
        }
      })


    },
    reply(pid, target) {
      if (this.user.username == null){
        this.$message.warning("请登录")
        return;
      }
      this.replyComment = {pid: pid, userId: this.user.id, foreignId: this.id}
      this.dialogFormVisible3 = true
    },
    load2() {
      this.request.get("/comment/tree", {
        params: {
          foreignId: this.id,
        }
      }).then(res => {
        this.comments = res.data
      })
    },
    saveReply() {
      if (this.user.username == null){
        this.$message.warning("请登录")
        return;
      }
      this.replyComment.foreignId = this.id;
      this.request.post("/comment", this.replyComment).then(res => {
        if (res.code === '200') {
          this.$message.success("评论成功")
          this.replyComment = {}
          this.dialogFormVisible3 = false
          this.load2()
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    submit2() {
      if (this.user.username == null){
        this.$message.warning("请登录")
        return;
      }
      this.comment.userId = this.user.id
      this.comment.foreignId = this.id
      this.request.post("/comment", this.comment).then(res => {
        if (res.code === '200') {
          this.$message.success("评论成功")
          this.load2()
          this.comment = {}
        } else {
          this.$message.error("评论失败")
        }
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