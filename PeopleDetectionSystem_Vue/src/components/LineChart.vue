<template>
    <el-tabs tab-position="right" class="demo-tabs">
        <el-tab-pane label="日">
            <LineDay />
        </el-tab-pane>
        <el-tab-pane label="周">
            <LineWeek />
        </el-tab-pane>
        <el-tab-pane label="月">
            <LineMonth />
        </el-tab-pane>
        <el-tab-pane label="年">
            <LineYear />

        </el-tab-pane>
    </el-tabs>
</template>
  
<script>
import { reactive, computed, watchEffect, onMounted } from 'vue'
import LineDay from './LineDay.vue'  //引入组件
import LineWeek from './LineWeek.vue'  //引入组件
import LineMonth from './LineMonth.vue'  //引入组件
import LineYear from './LineYear.vue'  //引入组件
import axios from 'axios'          //引入axios
export default {
    //组件名
    name: 'LineChart',

    components: {
        LineDay,    //注册组件
        LineWeek,
        LineMonth,
        LineYear
    },


    setup() {     //接收到的参数需要传入setup才能在setup中使用
        //数据
        let data = reactive({


        })

    

    
        //生命周期函数，
        //onMounted：在初始化页面完成后执行
        onMounted(() => {
            



        })


        //数据和函数都要返回
        return {
            data,

        }
    },




}
</script>
<style>
.demo-tabs {
    height: 100%;
}

.demo-tabs>.el-tabs__content {
    height: 100%;
}

.demo-tabs .el-tab-pane {
    height: 100%;
}

</style>
  