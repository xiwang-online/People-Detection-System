<template>
    <div class="con" v-if="data.showflag">
        <el-table :data="data.users" style="width: 100%" stripe>
            <el-table-column fixed prop="id" label="用户编号" width="120" />
            <el-table-column fixed prop="name" label="用户名" width="150" />
            <el-table-column prop="password" label="用户密码" width="150" />
            <el-table-column prop="usertype" label="用户级别" width="150" />
            <el-table-column prop="phone" label="电话" width="150" />
            <el-table-column prop="avatar" label="头像" width="150" :show-overflow-tooltip='true'>
                <template #default="scope">
                    <img :src="scope.row.avatar" alt="" style="width: 32px;height:32px">
                </template>
            </el-table-column>
            <el-table-column prop="remark" label="备注" />

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
            <span>用户编号:{{ data.modifydata.id }}</span><br>
            <span>用户名：</span>
            <el-input v-model="data.modifydata.name" />
            <span>用户密码:</span>
            <el-input v-model="data.modifydata.password" />
            <span>用户级别:</span>
            <el-input v-model="data.modifydata.usertype" />
            <span>电话:</span>
            <el-input v-model="data.modifydata.phone" />
            <span>头像:</span>
            <el-input v-model="data.modifydata.avatar" />
            <span>备注:</span>
            <el-input v-model="data.modifydata.remark" />
            <el-button class="but" type="primary" @click="modifysubmit">提交</el-button>
        </el-dialog>

        <el-dialog v-model="data.addflag" title="添加">
            <span>用户名：</span>
            <el-input v-model="data.adddata.name" />
            <span>用户密码:</span>
            <el-input v-model="data.adddata.password" />
            <span>用户级别:</span>
            <el-input v-model="data.adddata.usertype" />
            <span>电话:</span>
            <el-input v-model="data.adddata.phone" />
            <span>头像:</span>
            <el-input v-model="data.adddata.avatar" />
            <span>备注:</span>
            <el-input v-model="data.adddata.remark" />
            <el-button class="but" type="primary" @click="addsubmit">提交</el-button>
        </el-dialog>
    </div>
    <div class="con" v-if="!data.showflag">
        <el-empty description="您无权限查看本页信息！" />
    </div>
</template>
  
<script>
import { reactive, computed, watchEffect, onMounted } from 'vue'
import axios from 'axios'          //引入axios
import { ElMessage } from 'element-plus'
export default {
    //组件名
    name: 'User',
    setup() {
        let data = reactive({
            users: [],
            showflag: true,
            modifyflag: false,   //修改框是否显示
            modifydata: {},  //要修改的数据
            addflag: false,   //添加框是否显示
            adddata: {
                "name": "",
                "password": "",
                "usertype": "",
                "phone": "",
                "avatar": "",
                "remark": ""

            }       //要添加的内容

        })


        function modify(da) {
            data.modifyflag = true,
                data.modifydata = da
        }


        function modifysubmit() {
            axios.post("api/user/update", data.modifydata)
                .then(function (value) {
                    if (value.data == true) {
                        data.modifyflag = false
                        console.log(data.users)
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
            axios.post("api/user/delete/" + id)
                .then(function (value) {
                    if (value.data == true) {
                        data.users = data.users.filter((user) => { return user.id != id })   //从数组中去掉被删掉的数据
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
                    } else if (reason.response.status == 400) {
                        ElMessage({
                            message: '请按要求填写。',
                            type: 'warning',
                        })
                    }
                })
        }



        function add() {
            data.addflag = true

        }

        function addsubmit() {
            axios.post("api/user/save", data.adddata)
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
                    } else if (reason.response.status == 400) {
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
            //获取摄像头数据
            axios.get('api/user').then(res => {
                data.users = res.data
            }, err => {
                console.log(err.response.status);
                if (err.response.status == 401) {          //未登录，重定向到登录页面
                    router.push("login");
                } else if (err.response.status == 403) {
                    data.showflag = false

                }
            })


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
<style scoped>
.con {
    width: 100%;
    height: 100%;
}
</style>
  