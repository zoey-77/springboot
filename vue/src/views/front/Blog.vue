<template>
  <div style="margin: 15px auto">
    <div>
      <div>
        <div style="color: #1E90FF; font-size: 20px;font-weight: bold">
          交流论坛
        </div>
        <el-row>
          <el-col :span="24">
            <div v-for="item in tableData" :key="item.id" style="margin: 10px 0">
              <el-card>
                <div style="display: flex">
                  <div style="flex: 1">
                    <div style="font-size: 23px; font-weight: bold;cursor: pointer" @click="$router.push('/front/blogDetail?id=' + item.id)" class="name">{{ item.name }}</div>
                    <div style="margin: 10px 0; color: #666"></div>
                    <div style="margin-top: 57px; color: #666">
                      <span><i class="el-icon-user"></i> {{ item.user }}</span>
                      <span style="margin-left: 10px"><i class="el-icon-time"></i> {{ item.time }}</span>
                    </div>
                  </div>
                  <div style="width: 200px">
                    <img :src="item.img" alt="" style="width: 200px; height: 110px">
                  </div>
                </div>
              </el-card>
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

  </div>
</template>

<script>
export default {
  name: "FrontHome",
  data() {
    return {
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
      this.request.get("/blog/page/front", {
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
