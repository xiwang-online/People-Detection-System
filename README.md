<p align="center">
	<a href="https://github.com/xiwang-online/People-Detection-System">
		<img src="./img/logo.png" alt="People Detection" /><br />
		<b>People Detection System</b>
	</a><br />
	<br />
	<b>人流量检测系统</b><br />
	<b>深度学习|人工智能|数据分析|VUE|SpringBoot</b>
</p>



**本人毕业论文涉及到本项目相关内容，故暂时删掉项目中部分内容，待盲审通过后恢复与。**


## 简介
最近人工智能大火，之前也做了一些相关的项目，有软件有硬件，但大都不够完整，没什么实用价值，所以就一直想做一个完整点的项目。于是经过一段时间的构思和十来天的代码时间后就有了现在这个项目。迫于能力有限、学业繁忙，未能完全达到最初想要的效果，一些功能也暂未实现。但至少能看出是个比较完整的项目。  
项目主要分为三部分，算法、前端和后端，算法读取摄像头数据并实时处理，将处理后的结果发给后端保存，处理后的视频经流媒体发给前端显示；前端使用Vue框架，结合element-ui、Echarts实现了对人流量数据的图表分析功能。后端采用SpringBoot，实现数据的接收发送与用户权限管理。  
项目仅为练手之作，疏漏颇多，还请谅解，后续若有闲暇还会完善。  


## 技术栈
- **前端：** vue3 + element-ui + Echarts + 高德地图api
- **后端：** spring boot + mysql
- **人工智能：** pytorch + yolov5 + deepsort
- **部署：** docker + nginx

## 功能特色
- 可动态的添加删除摄像头，扩展性好;
- 可直接接入现有摄像头，无需单独安装;
- 可以以日、周、月、年为单位进行人流量分析;
- 可对用户进行权限管理，不同用户拥有不同的权限;
- 可根据摄像头位置坐标，实时查看摄像头位置;
- 人数信息实时更新;
- 多端登入，方便快捷（暂未优化手机端）。


## 项目运行
#### 数据库
1. 创建mysql数据库
2. 根据mysql文件夹下的sql文件初始化数据

#### 后端
1. 进入Spring boot文件夹
2. 修改application.yml，配置数据源和端口
3. 运行入口文件：```PeopleDetectionSystemSpringBootApplication.class```

####  前端
1. 进入Vue文件夹
2. 注册[高德开放平台](https://lbs.amap.com/)账号，申请key，填入项目index.html的16和19行。
3. 修改vite.config.js文件的target为后端地址
4. 修改video.vue中的流媒体服务器地址。
5. 在项目目录运行命令下载并安装包文件：```cnpm install```
6. 运行：```npm run dev```

#### 流媒体服务器
本项目采用nginx搭建流媒体服务器，需安装nginx和nginx-rtmp-module插件，建议使用 [docker-nginx-rtmp](https://github.com/alfg/docker-nginx-rtmp)，环境都集成好了，直接用就可以了。

#### 深度学习算法
1. 安装[ffmpeg](https://ffmpeg.org/)
2. 运行环境：
    - python 3.6+，pip 20+
    - pytorch
    - pip install -r requirements.txt
3. 修改main.py中的数据上传地址和流媒体服务器地址
4. 配置好环境后直接运行main.py：```python main.py```


## 参考资料
- [VUE官网](https://vuejs.org/)
- [Element-UI](https://element-plus.org/zh-CN/)
- [Echarts](https://echarts.apache.org/zh/index.html)
- [高德地图开放平台](https://console.amap.com/)
- [docker-nginx-rtmp](https://github.com/alfg/docker-nginx-rtmp)
- [yolov5 deepsort 行人 车辆 跟踪 检测 计数](https://github.com/dyh/unbox_yolov5_deepsort_counting)