<template>
    <div class="block">
        <el-date-picker v-model="data.year1" value-format="YYYY" type="year" placeholder="本年" @change="yeardatechange" />

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
    name: 'LineYear',

    setup() {
        const store = useStore()
        const router = useRouter() 
        let data = reactive({
            year1: "",
            peryear: {},
            cameras: {},

        })


        //表配置
        data.option = computed(() => {     //属性直接追加到data中
            return {
                title: {
                    text: '年人流量'
                },
                tooltip: {
                    trigger: 'axis',
                },
                legend: {
                    show: true, //是否显示
                    data: Object.keys(data.peryear),            //将所有键转化为数组
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
                    data: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
                },
                yAxis: {
                    type: 'value'
                },
                series: [

                ]



            }
        })


        //将获取到的人流量信息更新到图表option中
        function peryearToOption() {
            var keys = Object.keys(data.peryear);
            for (var i = 0; i < keys.length; i++) {
                if (keys[i] == 0) {
                    data.option.legend.data[i] = "总计";
                    data.option.series[i] = {
                        name: "总计",
                        type: 'line',
                        smooth: true,
                        data: data.peryear[keys[i]]
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
                        data: data.peryear[keys[i]]
                    }
                }
            }
        }

        //日历组件更新值时触发
        //日
        function yeardatechange() {
            axios.get('api/detection/peryear/' + data.year1).then(res => {
                data.peryear = res.data
                peryearToOption()
            }, err => {
                console.log(err);
                if (err.response.status == 401) {          //未登录，重定向到登录页面
                    router.push("login");
                }
            })
        }

        function getFormattedDate() {
            const now = new Date();
            const year = now.getFullYear();
            return year;
        }

        //onMounted：在初始化页面完成后执行
        onMounted(() => {
            data.cameras = store.state.camera

            //默认展示当天的数据
            axios.get('api/detection/peryear/' + getFormattedDate()).then(res => {
                data.peryear = res.data
                peryearToOption()
            }, err => {
                console.log(err);
            })


        })


        //数据和函数都要返回
        return {
            data,
            yeardatechange,
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
  