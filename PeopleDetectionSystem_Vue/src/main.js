import { createApp } from 'vue'
import router from './router'       //引入路由
import store from './store'         //引入vuex
import axios from 'axios'          //引入axios
import VueAxios from 'vue-axios'    //引入vue-axios
import ECharts from 'vue-echarts'    //引入echarts
import "echarts";                   //全局引入echarts，为了减少打包体积，可以按模块引入：https://github.com/ecomfe/vue-echarts/blob/main/README.zh-Hans.md
import App from './App.vue'
import './index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'        //element-ui图标
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';


const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(router).use(store).use(VueAxios,axios).use(ElementPlus)
.component('v-chart',ECharts)
.mount('#app')



axios.defaults.baseURL = 'http://localhost:3000';  //设置默认路由前缀
