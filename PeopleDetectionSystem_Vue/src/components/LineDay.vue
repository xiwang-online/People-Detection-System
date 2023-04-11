<template>
    <div class="block">
        <el-date-picker v-model="data.day1" value-format="YYYY-MM-DD" type="date" placeholder="今日"
            @change="daydatechange" />
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
    name: 'LineDay',

    setup() {
        const store = useStore()
        const router = useRouter()
        let data = reactive({
            day1: "",
            perday: {},
            cameras: {},

        })


        //表配置
        data.option = computed(() => {     //属性直接追加到data中
            return {
                title: {
                    text: '单日人流量'
                },
                tooltip: {
                    trigger: 'axis',
                },
                legend: {
                    show: true, //是否显示
                    data: Object.keys(data.perday),            //将所有键转化为数组
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
                    data: ['0:00', '1:00', '2:00', '3:00', '4:00', '5:00', '6:00', '7:00', '8:00', '9:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00']
                },
                yAxis: {
                    type: 'value'
                },
                series: [

                ]



            }
        })


        //将获取到的人流量信息更新到图表option中
        function perdayToOption() {
            var keys = Object.keys(data.perday);
            for (var i = 0; i < keys.length; i++) {
                if (keys[i] == 0) {
                    data.option.legend.data[i] = "总计";
                    data.option.series[i] = {
                        name: "总计",
                        type: 'line',
                        smooth: true,
                        data: data.perday[keys[i]]
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
                        data: data.perday[keys[i]]
                    }
                }
            }
        }

        //日历组件更新值时触发
        //日
        function daydatechange() {
            axios.get('api/detection/perday/' + data.day1).then(res => {
                data.perday = res.data
                perdayToOption()
            }, err => {
                console.log(err);
            })
        }

        function getFormattedDate() {
            const now = new Date();
            const year = now.getFullYear();
            const month = now.getMonth() + 1;
            const day = now.getDate();
            const date = `${year}-${month}-${day}`;
            return date;
        }

        //onMounted：在初始化页面完成后执行
        onMounted(() => {
            data.cameras = store.state.camera


            //默认展示当天的数据
            axios.get('api/detection/perday/' + getFormattedDate()).then(res => {
                data.perday = res.data
                perdayToOption()
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
            daydatechange,
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
  