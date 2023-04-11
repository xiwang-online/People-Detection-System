import cv2
import numpy as np
import requests
import json
# # 打开摄像头，参数代表设备
# cap = cv2.VideoCapture('rtsp://admin:admin@10.4.62.158')
# cv2.namedWindow('video', cv2.WINDOW_NORMAL)
# cv2.resizeWindow('video', 1080, 720)

# fps = cap.get(cv2.CAP_PROP_FPS)
# # 循环读取每一帧
# while cap.isOpened():
#     # read()返回两个数据，第一个表示是否读到数据，true表示读到，frame表示读到的数据
#     ret, frame = cap.read()
#     if not ret:
#         break
#     print("fps:"+ str(fps))
#     # 显示数据
#     cv2.imshow('video',frame)
#     if cv2.waitKey(10) & 0xFF == ord('q'):
#         break
    
# # 要释放资源
# cap.release()

# cv2.destroyAllWindows()



data ={
    "key":"123",
    "inout1":"1",
    "camera":"1"
}
req = requests.post("http://10.4.62.154:8081/api/detection",data=json.dumps(data))  # 发post请求,以json字符串参数格式
print(req)