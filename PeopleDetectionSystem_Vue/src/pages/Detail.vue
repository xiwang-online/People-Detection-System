<template>
    <el-table :data="data.detectionData" style="width: 100%" stripe height="88vh">
        <el-table-column align="center" prop="date" label="日期" width="110" />
        <el-table-column align="center" prop="camera" label="摄像头编号" width="100" />
        <el-table-column align="center" prop="inout1" label="出入(0入1出)" width="110"/>
        <el-table-column align="center" prop="one" label="0:00-1:00" width="110"/>
        <el-table-column align="center" prop="two" label="1:00-2:00" width="110"/>
        <el-table-column align="center" prop="three" label="2:00-3:00" width="110"/>
        <el-table-column align="center" prop="four" label="3:00-4:00" width="110"/>
        <el-table-column align="center" prop="five" label="4:00-5:00" width="110"/>
        <el-table-column align="center" prop="six" label="5:00-6:00" width="110"/>
        <el-table-column align="center" prop="seven" label="6:00-7:00" width="110"/>
        <el-table-column align="center" prop="eight" label="7:00-8:00" width="110"/>
        <el-table-column align="center" prop="nine" label="8:00-9:00" width="110"/>
        <el-table-column align="center" prop="ten" label="9:00-10:00" width="110"/>
        <el-table-column align="center" prop="eleven" label="10:00-11:00" width="110"/>
        <el-table-column align="center" prop="twelve" label="11:00-12:00" width="110"/>
        <el-table-column align="center" prop="thirteen" label="12:00-13:00" width="110"/>
        <el-table-column align="center" prop="fourteen" label="13:00-14:00" width="110"/>
        <el-table-column align="center" prop="fifteen" label="14:00-15:00" width="110"/>
        <el-table-column align="center" prop="sixteen" label="15:00-16:00" width="110"/>
        <el-table-column align="center" prop="seventeen" label="16:00-17:00" width="110"/>
        <el-table-column align="center" prop="eighteen" label="17:00-18:00" width="110"/>
        <el-table-column align="center" prop="nineteen" label="18:00-19:00" width="110"/>
        <el-table-column align="center" prop="twenty" label="19:00-20:00" width="110"/>
        <el-table-column align="center" prop="twentyone" label="20:00-21:00" width="110"/>
        <el-table-column align="center" prop="twentytwo" label="21:00-22:00" width="110"/>
        <el-table-column align="center" prop="twentythree" label="22:00-23:00" width="110"/>
        <el-table-column align="center" prop="twentyfour" label="23:00-24:00" width="110"/>
    </el-table>
    <div class="example-pagination-block">
        <el-pagination layout="prev, pager, next" :total="data.total" :page-size="data.pageSize"
            @current-change="pagechange" />
    </div>
</template>
  
<script>
import { reactive, computed, watchEffect, onMounted } from 'vue'
import axios from 'axios'          //引入axios
export default {
    //组件名
    name: 'Detail',
    setup() {
        let data = reactive({
            currentPage: 1,     //当前页
            pageSize: 56,        //每页条数
            total: 0,           //总共条目数
            detectionData: []

        })

        function pagechange(page) {
            data.currentPage = page
            axios.get('api/detection/' + data.currentPage + '/' + data.pageSize).then(res => {
                data.detectionData = res.data.records
                data.total = res.data.total
            }, err => {
                console.log(err);
            })

        }



        //生命周期函数，
        //onMounted：在初始化页面完成后执行
        onMounted(() => {
            axios.get('api/detection/' + data.currentPage + '/' + data.pageSize).then(res => {
                data.detectionData = res.data.records
                data.total = res.data.total
                console.log(res.data.records)
            }, err => {
                console.log(err);
            })


        })


        //数据和函数都要返回
        return {
            data,
            pagechange
        }
    },



}
</script>
  