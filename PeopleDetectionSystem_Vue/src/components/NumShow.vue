<template>
    <div class="now numCon">
        <div class="nowpeople">当前人数：</div>
        <div class="nowpeoplenum">{{ data.nowpeople }} 人</div>
        <div class="pro">
            <el-progress :percentage="parseInt(data.nowpeople) / 100" :color="data.colors" />
        </div>
        <div class="peo">可承载人数：{{ data.nowpeople }}人/10000人</div>
    </div>
    <div class="today numCon icon">
        <img src="../assets/day.svg" style="width: 40px;height: 40px;">
        <div class="numright">
            <div>当天人次</div>
            <div class="numin">{{ data.todaypeople }}人</div>
        </div>
    </div>
    <div class="week numCon icon">
        <img src="../assets/week.svg" style="width: 40px;height: 40px;">
        <div class="numright">
            <div>本周人次</div>
            <div class="numin">{{ data.monthpeople }}人</div>
        </div>
    </div>
    <div class="month numCon icon">
        <img src="../assets/month.svg" style="width: 45px;height: 45px;">
        <div class="numright">
            <div>本月人次</div>
            <div class="numin">{{ data.monthpeople }}人</div>
        </div>
    </div>
    <div class="en numCon icon">
        <img src="../assets/sum.svg" style="width: 45px;height: 45px;">
        <div class="numright">
            <div>累计人次</div>
            <div class="numin">{{ data.sum }}人</div>
        </div>
    </div>
    <div class="en numCon icon">
        <img src="../assets/time.svg" style="width: 45px;height: 45px;">
        <div class="numright">
            <div>运营时长</div>
            <div class="numin">{{ data.timeold }}</div>
        </div>
    </div>
</template>
  
<script>
import { reactive, computed, watchEffect, onMounted, onBeforeUnmount } from 'vue'
import { useStore } from 'vuex'
import axios from 'axios'          //引入axios
import { useRouter } from "vue-router";
export default {
    //组件名
    name: 'NumShow',


    setup() {     
        const store = useStore()
        const router = useRouter()
        let data = reactive({
            nowpeople: 0,
            todaypeople: 0,
            monthpeople: 0,
            sum: 0,             //累计总人数
            timeold: "",
            inter:"",
            colors: [
                { color: '#f56c6c', percentage: 20 },
                { color: '#e6a23c', percentage: 40 },
                { color: '#5cb87a', percentage: 60 },
                { color: '#1989fa', percentage: 80 },
                { color: '#6f7ad3', percentage: 100 },
            ]


        })

        

        function getFormattedDate() {
            const now = new Date();
            const year = now.getFullYear();
            const month = now.getMonth() + 1;
            const day = now.getDate();
            const date = `${year}-${month}-${day}`;
            return date;
        }


        function getFormattedMonth() {
            const now = new Date();
            const year = now.getFullYear();
            const month = now.getMonth() + 1 <= 9 ? "0" + (now.getMonth() + 1) : (now.getMonth() + 1);
            const date = `${year}-${month}`;

            return date;
        }


        function init() {
            axios.get('api/detection/nowpeople').then(res => {
                data.nowpeople = res.data;


            }, err => {
                console.log(err);
                if (err.response.status == 401) {          //未登录，重定向到登录页面
                    router.push("login");
                }
            })


            //获取当天的数据
            axios.get('api/detection/perday/' + getFormattedDate()).then(res => {
                data.todaypeople = res.data[0].reduce((a, b) => a + b, 0)   //通过reduce对数组求和
            }, err => {
                console.log(err);
                if (err.response.status == 401) {          //未登录，重定向到登录页面
                    router.push("login");
                }
            })


            //获取当月的数据
            axios.get('api/detection/permonth/' + getFormattedMonth()).then(res => {
                data.monthpeople = res.data[0].reduce((a, b) => a + b, 0)   //通过reduce对数组求和

            }, err => {
                console.log(err);
                if (err.response.status == 401) {          //未登录，重定向到登录页面
                    router.push("login");
                }
            })

            //获取累计总人数
            axios.get('api/detection/sum/').then(res => {
                data.sum = res.data
            }, err => {
                console.log(err);
                if (err.response.status == 401) {          //未登录，重定向到登录页面
                    router.push("login");
                }
            })




            //计算运行时间
            var dateObj = new Date();   //当前时间
            var grt = new Date("1/1/2023 00:00:00");//建站时间

            //计算运行时间
            var days = (dateObj - grt) / 1000 / 60 / 60 / 24;
            var dnum = Math.floor(days);   //天
            var hours = (dateObj - grt) / 1000 / 60 / 60 - (24 * dnum);
            var hnum = Math.floor(hours);   //时
            if (String(hnum).length == 1) {
                hnum = "0" + hnum;
            }
            var minutes = (dateObj - grt) / 1000 / 60 - (24 * 60 * dnum) - (60 * hnum);
            var mnum = Math.floor(minutes);  //分
            if (String(mnum).length == 1) {
                mnum = "0" + mnum;
            }
            var seconds = (dateObj - grt) / 1000 - (24 * 60 * 60 * dnum) - (60 * 60 * hnum) - (60 * mnum);
            var snum = Math.round(seconds);    //秒
            if (String(snum).length == 1) {
                snum = "0" + snum;
            }
            //data.timeold = dnum + "天" + hnum + "时" + mnum + "分" + snum + "秒"
            data.timeold = dnum + "天" + hnum + "时"

        }


        //生命周期函数，
        //onMounted：在初始化页面完成后执行
        onMounted(() => {
            data.inter = setInterval(init, 1000);

        })

        onBeforeUnmount(()=>{
            clearInterval(data.inter);
        })




        //数据和函数都要返回
        return {
            data,

        }
    },




}
</script>
<style scoped>


.numCon {
    height: 100%;
    background-color: #ffffff;
    border-radius: 10px;
    font-family: SimHei;
    /* 设置一个通用的圆角 */
}

.now {
    width: 20%;
    background-color: #518aff;
    color: #ffffff;
    /*
    background-image: linear-gradient(to bottom left, #3fcfe0, #76dce9);
*/
}

.nowpeople {
    font-size: 20px;
    font-weight: 700;
    margin: 8px;

}

.nowpeoplenum {
    font-size: 32px;
    font-weight: bold;
    text-align: center;
    margin-top: 20px;
    Letter-spacing: 4px
}

.pro {
    margin-left: 20px;
    margin-top: 10px;
}

.peo {
    margin-left: 20px;
    font-size: small;
}

.today {
    width: 15%;
}

.week {
    width: 15%;
}

.month {
    width: 15%;
}

.en {
    width: 15%;
}



.icon {
    display: flex;
    justify-content: center;
    align-items: center
}

.icon img {
    margin-right: 20px;
}

.numright {
    text-align: center;
}

.numin {
    font-size: 22px;
    margin-top: 10px;
}

</style>
  