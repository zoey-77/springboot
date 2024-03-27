import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/gloable.css'
import request from "@/utils/request";

['success', 'warning', 'info', 'error'].forEach(type => {
  ElementUI.Message[type] = options => {
    if (typeof options === 'string') {
      options = {
        message: options
      };
      options.duration = 2000
    }
    options.type = type;
    return ElementUI.Message(options);
  };
});

Vue.config.productionTip = false

Vue.use(ElementUI, { size: "mini" });

Vue.prototype.request=request

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
