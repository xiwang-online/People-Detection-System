<template>
    <div class="con">
        <div id="container"></div>
        <div v-show="data.show" id="note" class="note">{{ data.camera }}</div>
    </div>
</template>
  
<script>
import { reactive, computed, watchEffect, onMounted } from 'vue'
import axios from 'axios'          //引入axios
import { useStore } from 'vuex'
export default {
    //组件名
    name: 'Map',
    setup() {
        const store = useStore()
        let data = reactive({
            cameras: {},
            camera: "",
            show: false


        })

        function map() {
            //创建地图组件
            var map = new AMap.Map(document.getElementById("container"), {
                zoom: 18,
            });

            //设置为室内地图   https://lbs.amap.com/api/javascript-api/guide/layers/official-layers
            map.on('indoor_create', function () {
                map.indoorMap.showIndoorMap('B0FFIL7S89', 1);
            })

            //添加上交通图
            var traffic = new AMap.TileLayer.Traffic({
                'autoRefresh': true,     //是否自动刷新，默认为false
                'interval': 180,         //刷新间隔，默认180s
            });
            map.add(traffic); //通过add方法添加图层


            // 创建一个 Icon
            var startIcon = new AMap.Icon({
                // 图标尺寸
                size: new AMap.Size(40, 40),
                // 图标的取图地址
                image: '../src/assets/camera.svg',
                // 图标所用图片大小
                imageSize: new AMap.Size(40, 40),
                // 图标取图偏移量
                //imageOffset: new AMap.Pixel(-9, -3)
            });


            //[116.35532, 39.86050],


            var keys = Object.keys(data.cameras);

            for (var i = 0; i < keys.length; i++) {
                //摄像头数据
                var x = data.cameras[keys[i]].positionx
                var y = data.cameras[keys[i]].positiony

                // 将 icon 传入 marker
                var Marker = new AMap.Marker({
                    //位置
                    position: new AMap.LngLat(x, y),//以图标左上角为准
                    //使用的图标
                    icon: startIcon,
                    // 设置了 icon 以后，设置 icon 的偏移量，以 icon 的 [center bottom] 为原点
                    offset: new AMap.Pixel(-12, -20)
                });

                //添加属性，以便区分
                Marker.setExtData(keys[i])

                //为图标添加点击事件
                Marker.on('click', clicknote);
                //为图标添加点击事件
                Marker.on('mouseover', shownote);
                Marker.on('mouseout', closenote);


                map.add(Marker)

            }

        }

        //鼠标移入，显示标注
        function shownote(e) {
            data.show = true
            let text = document.getElementById("note");
            text.style.left = parseInt(e.pixel.getX()) - 85 + "px";
            text.style.top = parseInt(e.pixel.getY()) - 35 + "px";
            data.camera = data.cameras[e.target.getExtData()].name

        }

        function closenote(e) {
            data.show = false


        }


        function clicknote(e) {



        }



        //生命周期函数，
        //onMounted：在初始化页面完成后执行
        onMounted(() => {
            data.cameras = store.state.camera
            map()     //创建地图

        })


        //数据和函数都要返回
        return {
            data,
        }
    },




}
</script>
<style scoped>
.note {
    position: absolute;
    background-color: #ffffff;
    width: 80px;
    height: 30px;
    cursor: default;
    text-align: center;
    line-height: 1.8;
    border-radius: 5px;
    box-shadow: 0px 2px 5px #6d6d6d;
    /** 
    font-family: SimHei;  
    font-size: large;
    font-weight: bold;
    */
}

.con {
    width: 100%;
    height: 100%;
    position: relative;
}

#container {
    width: 100%;
    height: 100%;
}
</style>
  