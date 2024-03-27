<template>
  <div style="padding: 10px">
    <div style="color: #1E90FF; font-size: 20px;font-weight: bold">
      校猫地图
    </div>
    <el-card style="margin-top: 10px">
      <div>
        <Modal @on-cancel="cancel" v-model="showMapComponent"  :closable="false" :mask-closable="false">
          <baidu-map v-bind:style="mapStyle" class="bm-view" ak="rbjgevsMLkje2IrU3GynESAM1iuSpDDW"
                     :center="center"
                     :zoom="zoom"
                     :scroll-wheel-zoom="true"
                     @click="getClickInfo"
                     @moving="syncCenterAndZoom"
                     @moveend="syncCenterAndZoom"
                     @zoomend="syncCenterAndZoom">
            <bm-view style="width: 100%; height:870px;"></bm-view>
            <bm-marker v-for="(item,index) in mapPoint" :key="index" :position="item" :dragging="false" animation="BMAP_ANIMATION_BOUNCE">
            </bm-marker>
            <bm-control :offset="{width: '10px', height: '10px'}" >
              <!--              <bm-auto-complete v-model="keyword" :sugStyle="{zIndex: 999999}">-->
              <!--                <input type="text" placeholder="请输入搜索关键字" class="serachinput">-->
              <!--              </bm-auto-complete>-->
            </bm-control>
          </baidu-map>
        </Modal>
      </div>
    </el-card>
  </div>
</template>

<script>
import {BaiduMap, BmAutoComplete, BmControl, BmDriving, BmLocalSearch, BmMarker, BmView} from "vue-baidu-map";


export default {
  components: {
    BaiduMap,
    BmControl,
    BmView,
    BmAutoComplete,
    BmLocalSearch,
    BmMarker,
    BmDriving
  },
  name: "BlogDetail",
  data() {
    return {
      id: this.$route.query.id,
      rooms: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      form: {},
      comments: [],
      comment: {
        content: ''
      },
      replyComment: {},
      dialogFormVisible3: false,
      dialogFormVisible4: false,
      dialogFormVisible5: false,
      state: true,
      state2: true,
      content: '',
      showMapComponent: this.value,
      keyword: '',
      mapStyle: {
        // width: '100%',
        // height: this.mapHeight + 'px'
      },
      center: {lng: 103.001, lat:29.983},
      zoom: 17,
      mapPoint:[
          {
            lng: 102.99876,
            lat: 29.983259
          },
          {
            lng: 103.007308,
            lat: 29.984558
          },
        {
          lng: 103.001075,
          lat: 29.983126
        },
        {
          lng: 103.008811,
          lat: 29.986308
        },
        {
          lng: 103.008435,
          lat: 29.985274
        },
        {
          lng: 102.996533,
          lat: 29.986229
        },
        {
          lng: 103.001668,
          lat: 29.98468
        },
        {
          lng: 103.00906,
          lat: 29.985939
        }
      ],
      end: ''
    }
  },
  created() {
    this.load()
    this.load2()
  },
  watch: {
    value: function (currentValue) {
      this.showMapComponent = currentValue
      if (currentValue) {
        this.keyword = ''
      }

    }
  },
  props: {
    value: Boolean,
    mapHeight: {
      type: Number,
      default: 500
    }
  },
  methods: {
    /***
     * 地图点击事件。
     */
    getClickInfo (e) {
      this.center.lng = e.point.lng
      this.center.lat = e.point.lat
    },
    syncCenterAndZoom (e) {
      const {lng, lat} = e.target.getCenter()
      this.center.lng = lng
      this.center.lat = lat
      this.zoom = e.target.getZoom()
    },
    /***
     * 确认
     */
    confirm () {
      this.showMapComponent = false
      this.$emit('map-confirm', this.center)
      console.log(this.center);

    },
    /***
     * 取消
     */
    cancel () {
      this.showMapComponent = false
      this.$emit('cancel', this.showMapComponent)
    },
    load() {
      // this.request.get("/rooms/" + this.id).then(res => {
      //   this.rooms = res.data
      //
      //   let data = {lng: this.rooms.lng,lat:this.rooms.lat};
      //   this.mapPoint.push(data)
      // })
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