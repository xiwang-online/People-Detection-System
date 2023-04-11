<template>
    <div class="container">
        <div class="num">
            <NumShow /> 
        </div>
        <div class="maTa">
            <div class="table maTaCon">
                <PieChart />
            </div>
            <div class="map maTaCon"><!--地图-->
                <Map /> 
            </div>
            <div class="pic maTaCon">
                <div class="word">
                    <div>商场简介：</div>
                    <p>商场名称:XXX 商场</p>
                    <p>商场地址:XXX 市 XXX 区 XXX 路 XXX 号</p>
                    <p>商场简介:XXX 商场是一家大型综合性商场，总面积超过 XXX 平方米，拥有多个楼层和多样化的购物区。商场内部装修精美，设施完善，为消费者提供了舒适便利的购物环境。</p>
                    <p>商场经营范围广泛，涵盖了服装、鞋帽、化妆品、家居用品、餐饮等多个方面。商场内部还设有停车场，方便消费者购物停车。</p>
                </div>
            </div>
        </div>
        <div class="line">
            <LineChart />
        </div>
    </div>
</template>
  
<script>
import { reactive, computed, watchEffect, onMounted } from 'vue'
import LineChart from '../components/LineChart.vue'  //引入组件
import PieChart from '../components/PieChart.vue'
import Map from '../components/Map.vue'
import NumShow from '../components/NumShow.vue'

export default {
    //组件名
    name: 'Visualization',
    components: {
        LineChart,    //注册组件
        PieChart,
        Map,
        NumShow,

    },

    setup() {
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
<style scoped>

/*最外层容器*/
.container {
    background-color: #f1f8f8;
    height: 100%;
    width: 100%;
}

/*上层数据展示*/
.num {
    display: flex;
    height: 17%;
    justify-content: space-between;
    align-items: center;
}


.maTa {
    display: flex;
    height: 37%;
    justify-content: space-between;
    align-items: center;
}

.line {
    height: 45%;
    background-color: #ffffff;
    border-radius: 10px;
    /* 设置一个通用的圆角 */
}


.maTaCon {
    height: 85%;
    background-color: #ffffff;
    border-radius: 10px;
    /* 设置一个通用的圆角 */
}

.map {
    width: 40%;

}


.table {
    width: 30%;

}

.pic {
    width: 28%;
}

.word{
    margin: 20px;
    font-family: SimHei;
    height: 85%;
    overflow:auto
}
.word div{
    font-size: 18px;
    font-weight: bold;
    color: #444;
}

.word p{
    margin-top: 10px;
    font-size: 16px;
    color: #444;
}

/*宽度小于1350时*/
@media (max-width: 1350px) {}
</style>
  