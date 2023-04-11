# People-Detection-System

前端


问题：
- LineWeek有问题：没有设置默认显示本周，element-ui选择某一周后选择框里显示的时间不对。element-ui周选择器的周是从周日开始的，后端的周是从周一开始算的。
- 在PieChart中通过获取图表的dom来进行数值动态更新，LineChart中没有这么写，但是也能用，以后如果有需要可以再改一下，让使用的方法更一致，也更专业。另外要抽空学一下ref怎么用
- LineMonth中每月天数都不一样，目前设置为全部显示31天，后面还要改，可以通过新建一个二维数组，把每月的天数全部写出来，是哪个月就把哪个填进去就可以了。
- 引入amap失败，目前通过cdn导入amap
- 最新将摄像头数据直接写入vuex这样就不用每次都从后端获取了，但是Line和Pie中的代码还没改，后面有空再改。ps:不能保证在其他程序执行前已经获取到数据，其他程序读取到的值都是空，暂时别用vuex了把，home.vue中关于vuex的代码没有删，后面可以再想想办法。     ***解决***，办法是通过v-if让获取到数据后再让组件显示

- vite版本有些老了，装包经常报错


- 前端目录不能有中文路径




## 前端运行
- index.html
16行修改安全密钥 
    19行要填高德地图开放平台的key：https://console.amap.com/dev/index
- cnpm inatsll
- npm run dev



## 后端运行

- 新建数据库
- 修改application.yml数据源信息