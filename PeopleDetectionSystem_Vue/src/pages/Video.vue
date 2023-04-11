<template>
    <!-- 显示视频流 -->
    <div class="con">
    <video v-for='camera in data.cameras' :id='"video"+camera.id' class="video-js vjs-default-skin" autoplay muted preload="auto">
        <source :src="`http://localhost:8080/live/${camera.id}.m3u8`" type="application/x-mpegURL" >
    </video>
</div>
</template>
  
<script>
import { reactive, computed, watchEffect, onMounted } from 'vue'
import axios from 'axios'          //引入axios
import { useStore } from 'vuex'
export default {
    //组件名
    name: 'Video',
    setup() {
        const store = useStore()
        let data = reactive({
            cameras: store.state.camera


        })


        function initVideoSourc(a) {
            videojs(a, {
                bigPlayButton: false,
                textTrackDisplay: false,
                posterImage: false,
                errorDisplay: false,
                controlBar: true,
                // ...其他配置参数
            }, function () {
                this.play()
            })
        }





        //生命周期函数，
        //onMounted：在初始化页面完成后执行
        onMounted(() => {

            for (var i = 0; i < data.cameras.length; i++) {
                initVideoSourc("video" + data.cameras[i]["id"])

            }


        })


        //数据和函数都要返回
        return {
            data,

        }
    },



}
</script>

<style scoped>
.con {
    display: flex;
    flex-wrap:wrap
}

.video-js {
    margin: 0px;

}
</style>
  