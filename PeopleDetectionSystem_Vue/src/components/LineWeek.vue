<template>
    <div class="block">
        <el-date-picker v-model="data.week1" value-format="YYYY-MM-DD" type="week" format="[Week] ww" placeholder="本周"
            @change="weekdatechange" />
    </div>
    <div class="linechart1">
        <v-chart class="charts" :option="data.option" autoresize></v-chart>
    </div>
</template>
  
<script>
import { reactive, computed, watchEffect, onMounted } from 'vue'
import axios from 'axios'          //引入axios
import { useStore } from 'vuex'
import { useRouter } from "vue-router";
export default {
    //组件名
    name: 'LineWeek',

    setup() {
        const store = useStore()
        const router = useRouter()
        let data = reactive({
            week1: "",
            perweek: {},
            cameras: {},
            value1: "",

        })

        //周
        data.option = computed(() => {     //属性直接追加到data中
            return {
                title: {
                    text: '周人流量'
                },
                tooltip: {
                    trigger: 'axis',
                },
                legend: {
                    show: true, //是否显示
                    data: Object.keys(data.perweek),            //将所有键转化为数组
                    //data:[]

                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },

                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
                },
                yAxis: {
                    type: 'value'
                },
                series: [

                ]

            }
        })


        function perweekToOption() {
            var keys = Object.keys(data.perweek);
            for (var i = 0; i < keys.length; i++) {
                if (keys[i] == 0) {
                    data.option.legend.data[i] = "总计";
                    data.option.series[i] = {
                        name: "总计",
                        type: 'line',
                        smooth: true,
                        data: data.perweek[keys[i]]
                    }
                } else {
                    var camera = data.cameras.filter(function (camera) {   //找出摄像头id所对应的名字,此处返回的是数组
                        return camera.id == keys[i]
                    })
                    data.option.legend.data[i] = camera[0].name;
                    data.option.series[i] = {
                        name: camera[0].name,
                        type: 'line',
                        smooth: true,
                        data: data.perweek[keys[i]]
                    }
                }
            }
        }



        //日历组件更新值时触发
        //周
        function weekdatechange() {
            console.log(data.week1)
            axios.get('api/detection/perweek/' + data.week1).then(res => {
                data.perweek = res.data
                perweekToOption()
            }, err => {
                console.log(err);
            })
        }

        //onMounted：在初始化页面完成后执行
        onMounted(() => {
            data.cameras = store.state.camera


            //获取当前日期的前一个周日的日期
            var dateObj = new Date();   //当前时间
            var day = dateObj.getDay();  //周几
            var grt = new Date(24 * 60 * 60 * 1000 * day);//距离周日的时间
            var dateweek = new Date(dateObj - grt);   //周日时间
            var year = dateweek.getFullYear(); //年
            var month = dateweek.getMonth() + 1; //月  (注意：月份+1)
            var date = dateweek.getDate(); //日
            //补零
            if (month < 10) {
                month = "0" + month;
            }
            if (date < 10) {
                date = "0" + date;
            }
            var FormattedDate = year + "-" + month + "-" + date


            //默认展示本星期的数据
            axios.get('api/detection/perweek/' + FormattedDate).then(res => {
                data.perweek = res.data
                perweekToOption()
            }, err => {
                console.log(err);
                if (err.response.status == 401) {          //未登录，重定向到登录页面
                    router.push("login");
                }
            })


        })


        //数据和函数都要返回
        return {
            data,
            weekdatechange,
        }
    },




}
</script>

<style scoped>
.linechart1 {
    height: 100%;
    width: 100%;
}

.block {
    position: absolute;
    right: 5px;
    top: 5px;
    z-index: 9999;
}
</style>
  