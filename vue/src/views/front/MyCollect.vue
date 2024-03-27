<template>
  <div style="margin-top: 15px">
    <el-card>
      <el-table :data="tableData">
        <el-table-column prop="blog.name" label="博客"></el-table-column>
        <el-table-column label="封面"><template slot-scope="scope"><el-image style="width: 100px; height: 100px" :src="scope.row.blog.img" :preview-src-list="[scope.row.blog.img]"></el-image></template></el-table-column>
        <el-table-column prop="userName" label="用户"></el-table-column>
        <el-table-column prop="time" label="添加时间"></el-table-column>
      </el-table>
      <div style="padding: 10px 0">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[2, 5, 10, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "MyCollect",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},

    }
  },
  created() {
    if (this.user.username == null){
      this.$message.warning("请登录")
      return;
    }

    this.load()
  },
  methods: {
    load() {
      this.request.get("/collect/page", {
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
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
  }
}
</script>


<style>
.headerBg {
  background: #eee!important;
}
</style>
