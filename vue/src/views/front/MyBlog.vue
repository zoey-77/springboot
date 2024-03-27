<template>
  <div>
    <el-card>
      <div style="margin: 10px 0">
        <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      </div>

      <el-table :data="tableData">
        <el-table-column prop="name" label="标题"></el-table-column>
        <el-table-column prop="content" label="内容">
          <template v-slot="scope">
            <el-button type="primary" @click="view(scope.row.content)">查看内容</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="user" label="发布人"></el-table-column>
        <el-table-column prop="time" label="发布时间" width="150"></el-table-column>
        <el-table-column label="图片" width="140"><template slot-scope="scope"><el-image style="width: 100px; height: 100px" :src="scope.row.img" :preview-src-list="[scope.row.img]"></el-image></template></el-table-column>
        <el-table-column label="操作"  width="280" align="center">
          <template slot-scope="scope">
            <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
            <el-popconfirm
                class="ml-5"
                confirm-button-text='确定'
                cancel-button-text='我再想想'
                icon="el-icon-info"
                icon-color="red"
                title="您确定删除吗？"
                @confirm="del(scope.row.id)"
            >
              <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <div style="padding: 10px 0">
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
    </el-card>

    <el-dialog title="信息" :visible.sync="dialogFormVisible" width="60%" :close-on-click-modal="false">
      <el-form label-width="140px" size="small" style="width: 85%;" :model="form" :rules="rules" ref="ruleForm">
        <el-form-item prop="name" label="标题">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <div id="richText"></div>
        </el-form-item>
        <el-form-item prop="img" label="封面">
          <el-upload action="http://localhost:9090/file/upload" ref="img" :on-success="handleImgUploadSuccess">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="内容" :visible.sync="dialogFormVisible1" width="60%" :close-on-click-modal="false">
      <el-card>
        <div v-html="content"></div>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>
import E from "wangeditor"
let editor;

export default {
  name: "Blog",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      form: {},
      dialogFormVisible: false,
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      content: '',
      multipleSelection: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur'}
        ],
      },
      types: []
    }
  },
  created() {
    editor = ''
    this.load()
  },
  methods: {
    view(data){
      this.content = data
      this.dialogFormVisible1 = true;
    },
    load() {
      this.request.get("/blog/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

      this.request.get("/blogType").then(res => {
        this.types = res.data
      })
    },
    save() {
      this.form.content = editor.txt.html()
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.request.post("/blog", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("保存成功")
              this.dialogFormVisible = false
              this.dialogFormVisible2 = false
              this.load()
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    save2() {
      this.request.post("/blog", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("审核成功")
          this.dialogFormVisible2 = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
      this.$nextTick(() => {
        if (!editor){
          editor  = new E("#richText")
          // 配置 server 接口地址
          editor.config.uploadImgServer = 'http://localhost:9090/file/uploadImg'
          editor.config.uploadFileName = 'file'
          editor.create()
        }
        editor.txt.html('')

        if(this.$refs.img) {
          this.$refs.img.clearFiles();
        }
        if(this.$refs.file) {
          this.$refs.file.clearFiles();
        }
      })
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
      this.$nextTick(() => {
        if (!editor){
          editor = new E("#richText")
          // 配置 server 接口地址
          editor.config.uploadImgServer = 'http://localhost:9090/file/uploadImg'
          editor.config.uploadFileName = 'file'
          editor.create()
        }
        editor.txt.html(this.form.content)

        if(this.$refs.img) {
          this.$refs.img.clearFiles();
        }
        if(this.$refs.file) {
          this.$refs.file.clearFiles();
        }
      })
    },
    handleEdit2(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible2 = true
    },
    del(id) {
      this.request.delete("/blog/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      if (!this.multipleSelection.length) {
        this.$message.error("请选择需要删除的数据")
        return
      }
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/blog/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset() {
      this.name = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    handleFileUploadSuccess(res) {
      this.form.file = res
    },
    handleImgUploadSuccess(res) {
      this.form.img = res
    },
    download(url) {
      window.open(url)
    },
    exp() {
      window.open("http://localhost:9090/blog/export")
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    }
  }
}
</script>


<style>
.headerBg {
  background: #eee!important;
}
</style>
