<template>
  <div class="common-layout">
    <el-container class="container">
      <el-aside class="aside"><el-menu :default-active="data.active" router class="el-menu-vertical-demo">
          <div class="asideTop">XXX商场</div>
          <el-menu-item index="/">
            <el-icon>
              <PieChart />
            </el-icon>
            <template #title>数据总览</template>
          </el-menu-item>
          <el-menu-item index="/video">
            <el-icon>
              <View />
            </el-icon>
            <template #title>视频监控</template>
          </el-menu-item>
          <el-menu-item index="/detail">
            <el-icon>
              <document />
            </el-icon>
            <template #title>数据详情</template>
          </el-menu-item>
          <el-menu-item index="/camera">
            <el-icon>
              <Clock />
            </el-icon>
            <template #title>摄像头管理</template>
          </el-menu-item>
          <el-menu-item index="/user">
            <el-icon>
              <setting />
            </el-icon>
            <template #title>用户管理</template>
          </el-menu-item>
        </el-menu></el-aside>
      <el-container class="right">
        <el-header class="top">
          <div class="page">{{ data.nowpage }}</div>
          <div class="namedrop">
            <div class="name">
              <img :src="data.avatar" style="width: 32px;height:32px">
              <p>{{ data.username }}</p>
            </div>
            <div class="drop">
              <p>用户名：{{ data.username }}</p>
              <p>权限级别：{{ data.usertype }}</p>
              <p>电话：{{ data.phone }}</p>
              <p>备注：{{ data.reamrk }}</p>
              <el-button type="primary" @click="logout">退出登录</el-button>
            </div>
          </div>
        </el-header>
        <el-main>
          <RouterView v-if="data.flag"></RouterView>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
    
<script>
import { reactive, computed, watchEffect, onMounted } from 'vue'
import axios from 'axios'          //引入axios
import { useStore } from 'vuex'
import { useRouter } from "vue-router";
export default {
  //组件名
  name: 'Home',
  setup() {
    const store = useStore()
    const router = useRouter()
    let data = reactive({
      flag: false,       //等获取到摄像头数据后再让展示子组件
      active: router.currentRoute.value.fullPath,
      nowpage: "",
      username: "",
      usertype: "",
      avatar: "",
      userremark: "",
      phone: "",


    })



    function init() {
      //获取摄像头数据
      axios.get('api/camera').then(res => {
        store.commit('add', res.data)          //摄像头数据写入vuex
        data.flag = true                 //拿到数据后再显示子组件
      }, err => {
        console.log(err.response.status);
        if (err.response.status == 401) {          //未登录，重定向到登录页面
          router.push("login");
        }
      })
    }


    function logout() {
      localStorage.removeItem("name")
      router.push("login");
    }

    //监视,watchEffect函数中用到谁就监视谁,被监视的数据变了则执行函数，watchEffect比watch好用
    watchEffect(() => {
      data.active = router.currentRoute.value.fullPath
      if (data.active == "/") {
        data.nowpage = "数据总览"
      } else if (data.active == "/video") {
        data.nowpage = "视频监控"
      } else if (data.active == "/detail") {
        data.nowpage = "数据详情"
      } else if (data.active == "/camera") {
        data.nowpage = "摄像头管理"
      } else if (data.active == "/user") {
        data.nowpage = "用户管理"
      }


    })




    //生命周期函数，
    //onMounted：在初始化页面完成后执行
    onMounted(() => {
      init();
      data.username = window.localStorage.getItem("name")
      data.usertype = window.localStorage.getItem("usertype")
      data.avatar = window.localStorage.getItem("avatar")
      data.userremark = window.localStorage.getItem("reamrk")
      data.phone = window.localStorage.getItem("phone")



    })




    //数据和函数都要返回
    return {
      data,
      logout

    }
  },


}
</script>
<style>
.common-layout {
  width: 100%;
  height: 100vh;
}

.container {
  width: 100%;
  height: 100%
}

.el-menu-vertical-demo {
  height: 100%;
}

.right{
  background-color: #f1f8f8;
}


.aside {
  width: 200px;
}


.top {
  display: flex;
  justify-content: space-between;
  align-items: center;
}



.namedrop {
  display: flex;
  align-items: flex-end;
  flex-direction: column;
  z-index: 9999;
}


.name {
  display: flex;
  align-items: center;
}

.name p {
  margin-left: 10px;
  font-weight: bold;
}



.drop {
  display: none;
  width: 200px;

  background-color: #eee;
  position: absolute;
  top: 45px;
  padding: 15px;
  border-radius: 10px;
}


.namedrop:hover .drop {
  display: block;
}


.drop p {
  margin-bottom: 5px;
}


.page {
  font-weight: bold;

}

.asideTop {
  font-size: 24px;
  text-align: center;
  padding: 20px 0;

}


/*宽度小于1350时*/
@media (max-width: 1350px) {
  .aside {
    width: 15%;
  }

}
</style>
    