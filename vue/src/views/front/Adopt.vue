<template>
  <div style="width:1000px;margin: 15px auto">
      <div style="margin: 10px 0">
        <div style="color: #1E90FF; font-size: 20px;margin-bottom: 5px;font-weight: bold">
          猫咪领养
        </div>
        <el-row :gutter="10">
          <el-col :span="6" v-for="item in tableData" :key="item.id" style="margin-bottom: 10px">
            <div style="border: 1px solid #ccc; padding-bottom: 10px; border-radius: 10px; overflow: hidden">
              <img :src="item.img" alt="" style="width: 100%;height: 190px; cursor: pointer">
              <div style="padding: 5px; font-size: 18px;font-weight: bold; cursor: pointer;text-align: center;color: #494949">
                <span  @click="$router.push('/front/adoptDetail?id=' + item.id)">{{ item.name }}</span>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <div style="padding: 10px 0" v-if="tableData.length">
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
  name: "Cat",
  data() {
    return {
      files: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      tableData: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/adopt/page", {
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
.fontCss {
  font-size: 13px;
  color: #494949;
}
</style>
