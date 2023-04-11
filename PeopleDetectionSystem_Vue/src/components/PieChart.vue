<template>
    <v-chart ref="chart" class="charts" :option="data.option" ></v-chart>
</template>
  
<script>
import { ref, reactive, computed, watchEffect, onMounted } from 'vue'
import axios from 'axios'          //引入axios
import { useStore } from 'vuex'  
import { useRouter } from "vue-router";
export default {
    //组件名
    name: 'PieChart',
    setup() {
        const store = useStore()
        const router = useRouter()
        const chart = ref(null);   //获取到饼图的dom,以便进行更新

        let data = reactive({
            perday: {},
            cameras: {}
        })

        data.option = computed(() => {     //属性直接追加到data中
            return {
                title: {
                    text: '当日各位置人数分布',
                    subtext: '以进入人次统计',
                    left: 'center'
                },
                tooltip: {
                    //trigger: 'item'   //点击触发click事件
                },
                legend: {
                    //orient: 'vertical',   //垂直显示
                    orient: 'horizontal',   //水平显示 
                    x: 'center',
                    y: 'bottom'
                },
                series: [
                    {
                        name: 'Access From',
                        type: 'pie',
                        radius: '50%',
                        data: [

                        ],
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }

                ]
            }

        })

        //将获取到的人流量信息更新到图表option中
        function perdayToOption() {
            var keys = Object.keys(data.perday);
            var j = 0
            for (var i = 0; i < keys.length; i++) {
                if (keys[i] != 0) {
                    var camera = data.cameras.filter(function (camera) {   //找出摄像头id所对应的名字,此处返回的是数组
                        return camera.id == keys[i]
                    })

                    data.option.series[0].data[j] = {
                        value: data.perday[keys[i]].reduce((a, b) => a + b, 0),   //通过reduce对数组求和
                        name: camera[0].name
                    }
                    j++
                }
            }


            chart.value.setOption(data.option,true)   //更新饼图


        }

        function getFormattedDate() {
            const now = new Date();
            const year = now.getFullYear();
            const month = now.getMonth() + 1;
            const day = now.getDate();
            const date = `${year}-${month}-${day}`;
            return date;
        }


        //生命周期函数，
        //onMounted：在初始化页面完成后执行
        onMounted(() => {            
            data.cameras = store.state.camera

            //获取当天当天的数据
            axios.get('api/detection/perday/' + getFormattedDate()).then(res => {
                data.perday = res.data

                perdayToOption()    //数据更新到option
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
            chart
        }
    },




}
</script>
  