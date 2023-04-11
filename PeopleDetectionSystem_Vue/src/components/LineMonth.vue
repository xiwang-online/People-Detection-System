<template>
    <div class="block">
        <el-date-picker v-model="data.month1" type="month" value-format="YYYY-MM" placeholder="本月" @change=monthdatechange />
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
    name: 'LineMonth',

    setup() {
        const router = useRouter()
        const store = useStore()
        let data = reactive({
            month1: "",
            permonth: {},
            cameras: {},

        })


        //表配置
        data.option = computed(() => {     //属性直接追加到data中
            return {
                title: {
                    text: '月人流量'
                },
                tooltip: {
                    trigger: 'axis',
                },
                legend: {
                    show: true, //是否显示
                    data: Object.keys(data.permonth),            //将所有键转化为数组
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
                    //月份这里全部定为31天，可以优化一下
                    data: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31]
                },
                yAxis: {
                    type: 'value'
                },
                series: [

                ]



            }
        })


        //将获取到的人流量信息更新到图表option中
        function permonthToOption() {
            var month = [
                [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31],//1
                [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28],//2
                [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31],//3
                [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30],//4
                [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31],//5
                [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30],//6
                [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31],//7
                [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31],//8
                [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30],//9
                [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31],//10
                [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30],//11
                [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31],//12
            ]
            
            data.option.xAxis.data = month[parseInt(data.month1.split("-")[1])-1]    //修改表中每月天数



            var keys = Object.keys(data.permonth);
            for (var i = 0; i < keys.length; i++) {
                if (keys[i] == 0) {
                    data.option.legend.data[i] = "总计";
                    data.option.series[i] = {
                        name: "总计",
                        type: 'line',
                        smooth: true,
                        data: data.permonth[keys[i]]
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
                        data: data.permonth[keys[i]]
                    }
                }
            }
        }

        //日历组件更新值时触发
        //日
        function monthdatechange() {
            axios.get('api/detection/permonth/' + data.month1).then(res => {
                data.permonth = res.data
                permonthToOption()
            }, err => {
                console.log(err);
            })
        }

        function getFormattedDate() {
            const now = new Date();
            const year = now.getFullYear();
            const month = now.getMonth()+1 <= 9 ? "0"+ (now.getMonth()+1) : (now.getMonth()+1);
            const date = `${year}-${month}`;
            
            return date;
        }

        //onMounted：在初始化页面完成后执行
        onMounted(() => {
            data.cameras = store.state.camera


            //默认展示当天的数据
            axios.get('api/detection/permonth/' + getFormattedDate()).then(res => {
                data.permonth = res.data
                permonthToOption()
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
            monthdatechange,
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
  