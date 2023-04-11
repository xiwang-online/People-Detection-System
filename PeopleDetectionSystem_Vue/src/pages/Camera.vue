<template>
    <el-table :data="data.cameras" style="width: 100%" stripe>
        <el-table-column fixed prop="id" label="摄像头编号" width="120" />
        <el-table-column fixed prop="name" label="摄像头名称" width="150" />
        <el-table-column prop="ip" label="摄像头ip" width="150" />
        <el-table-column prop="positionx" label="摄像头坐标(经度)" width="150" />
        <el-table-column prop="positiony" label="摄像头坐标(纬度)" width="150" />
        <el-table-column prop="describe1" label="备注" />

        <el-table-column fixed="right" label="Operations" width="120">
            <template #default="scope">

                <el-button link type="primary" size="small" @click="modify(scope.row)">修改</el-button>

                <el-popconfirm title="确定要删除吗？" @confirm="delete1(scope.row.id)">
                    <template #reference>
                        <el-button link type="primary" size="small">删除</el-button>
                    </template>
                </el-popconfirm>
            </template>
        </el-table-column>
    </el-table>
    <el-button type="primary" @click="add">添加</el-button>




    <el-dialog v-model="data.modifyflag" title="修改">
        <span>ID:{{ data.modifydata.id }}</span><br>
        <span>摄像头名称：</span>
        <el-input v-model="data.modifydata.name" />
        <span>摄像头ip:</span>
        <el-input v-model="data.modifydata.ip" />
        <span>摄像头坐标(经度):</span>
        <el-input v-model="data.modifydata.positionx" />
        <span>摄像头坐标(纬度):</span>
        <el-input v-model="data.modifydata.positiony" />
        <span>备注:</span>
        <el-input v-model="data.modifydata.describe1" />
        <el-button class="but" type="primary" @click="modifysubmit">提交</el-button>
    </el-dialog>

    <el-dialog v-model="data.addflag" title="添加">
        <span>摄像头名称：</span>
        <el-input v-model="data.adddata.name" />
        <span>摄像头ip:</span>
        <el-input v-model="data.adddata.ip" />
        <span>摄像头坐标(经度):</span>
        <el-input v-model="data.adddata.positionx" />
        <span>摄像头坐标(纬度):</span>
        <el-input v-model="data.adddata.positiony" />
        <span>备注:</span>
        <el-input v-model="data.adddata.describe1" />
        <el-button class="but" type="primary" @click="addsubmit">提交</el-button>
    </el-dialog>
</template>
  
<script>
import { reactive, computed, watchEffect, onMounted } from 'vue'
import axios from 'axios'          //引入axios
import { useStore } from 'vuex'
import { useRouter } from "vue-router";
import { ElMessage } from 'element-plus'
export default {
    //组件名
    name: 'Camera',
    setup() {
        const router = useRouter()
        const store = useStore()
        let data = reactive({
            cameras: store.state.camera,
            modifyflag: false,   //修改框是否显示
            modifydata: {},  //要修改的数据
            addflag:false,   //添加框是否显示
            adddata:{
                "name":"",
                "ip":"",
                "positionx":"",
                "positiony":"",
                "describe1":""

            }       //要添加的内容



        })


        function modify(da) {
            if (window.localStorage.getItem("usertype") == "administrator") {
                data.modifyflag = true,
                    data.modifydata = da
            } else {
                ElMessage({
                    message: '您无权限修改，请联系管理员。',
                    type: 'warning',
                })

            }
        }

        function modifysubmit() {
            axios.post("api/camera/update", data.modifydata)
                .then(function (value) {
                    if (value.data == true) {
                        store.commit('add', data.cameras)          //修改vuex中的摄像头数据
                        data.modifyflag = false
                        ElMessage({
                            message: '修改成功',
                            type: 'success',
                        })
                    } else {
                        ElMessage({
                            message: '修改失败',
                            type: 'warning',
                        })
                    }
                })
                .catch(function (reason) {
                    console.log(reason)
                    if (reason.response.status == 401) {
                        ElMessage({
                            message: '您尚未登录',
                            type: 'warning',
                        })
                        router.push("login");
                    } else if (reason.response.status == 403) {
                        ElMessage({
                            message: '您无修改权限，请联系管理员。',
                            type: 'warning',
                        })
                    }
                })

            


        }


        function delete1(id) {
            if (window.localStorage.getItem("usertype") == "administrator") {
                axios.post("api/camera/delete/"+id)
                    .then(function (value) {
                        if (value.data == true) {
                            data.cameras = data.cameras.filter((camera) => { return camera.id != id })   //从数组中去掉被删掉的数据
                            store.commit('add', data.cameras)          //修改vuex中的摄像头数据
                            ElMessage({
                                message: '删除成功',
                                type: 'success',
                            })
                        } else {
                            ElMessage({
                                message: '删除失败',
                                type: 'warning',
                            })
                        }
                    })
                    .catch(function (reason) {
                        console.log(reason)
                        if (reason.response.status == 401) {
                            ElMessage({
                                message: '您尚未登录',
                                type: 'warning',
                            })
                            router.push("login");
                        } else if (reason.response.status == 403) {
                            ElMessage({
                                message: '您无删除权限，请联系管理员。',
                                type: 'warning',
                            })
                        } else if(reason.response.status == 400) {
                        ElMessage({
                            message: '请按要求填写。',
                            type: 'warning',
                        })
                    }
                    })

            } else {
                ElMessage({
                    message: '您无权限删除，请联系管理员。',
                    type: 'warning',
                })

            }


        }

        function add() {
            if (window.localStorage.getItem("usertype") == "administrator") {
                data.addflag = true
            } else {
                ElMessage({
                    message: '您无权限添加，请联系管理员。',
                    type: 'warning',
                })

            }

        }

        function addsubmit(){
            axios.post("api/camera/save", data.adddata)
                .then(function (value) {
                    if (value.data == true) {
                        data.addflag = false
                        ElMessage({
                            message: '添加成功',
                            type: 'success',
                        })

                        location.reload()  //用来刷新当前页面，就像刷新按钮一样。
                    } else {
                        ElMessage({
                            message: '添加失败',
                            type: 'warning',
                        })
                    }
                })
                .catch(function (reason) {
                    console.log(reason)
                    if (reason.response.status == 401) {
                        ElMessage({
                            message: '您尚未登录',
                            type: 'warning',
                        })
                        router.push("login");
                    } else if (reason.response.status == 403) {
                        ElMessage({
                            message: '您无添加权限，请联系管理员。',
                            type: 'warning',
                        })
                    } else if(reason.response.status == 400) {
                        ElMessage({
                            message: '请按要求填写。',
                            type: 'warning',
                        })
                    }
                })

            
            


        }



        //生命周期函数，
        //onMounted：在初始化页面完成后执行
        onMounted(() => {

        })


        //数据和函数都要返回
        return {
            data,
            modify,
            delete1,
            modifysubmit,
            add,
            addsubmit
        }
    },



}
</script>
  