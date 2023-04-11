package online.xiwang.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import online.xiwang.domain.Camera;
import online.xiwang.domain.Detection;
import online.xiwang.service.CameraService;
import online.xiwang.service.DetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;


@RestController   //返回值为json格式或者文本格式
@RequestMapping("/api/detection")
public class DetectionController {
    @Autowired
    private DetectionService detectionService;
    @Autowired
    private CameraService cameraService;


    /**
     * 查询全部记录
     * @return  返回全部记录
     */
    @GetMapping
    public List<Detection> getAll(){
        return detectionService.getAll();
    }

    /**
     * 更新人数，每访问一次，就在表中当前时间下人数+1
     * @param map  三个参数：key:密钥；inout1:出入；camera：摄像头编号
     * {
     * "key":"123",          //密钥
     * "inout1":"0",         //出入，0入1出
     * "camera":"1"          //摄像头编号
     * }
     * @return    成功返回200，不成功返回-1
     */
    @PostMapping
    public String update(@RequestBody Map<String,String> map){
        //先判断密钥对不对，再判断表中有没有当天的表，没有的话先创建表，再插入值
        if("123".equals(map.get("key"))){
            //1.利用空参构造创建simpleDateFormat对象，默认格式
            Date d1 = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String str1 = sdf1.format(d1);     //日期
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
            String str2 = sdf2.format(d1);    //小时
            Detection result = detectionService.SelectBydateCam(str1,map.get("inout1"), map.get("camera"));//查询记录
            if(result==null){    //没有当前日期的表，创建
                Detection det = new Detection();
                java.sql.Date sqlDate = new java.sql.Date(d1.getTime());
                det.setDate(sqlDate);
                det.setInout1(map.get("inout1"));
                det.setCamera(Integer.parseInt(map.get("camera")));
                detectionService.save(det);
                result = detectionService.SelectBydateCam(str1,map.get("inout1"), map.get("camera"));//创建记录之后要再查一遍，方便后续使用
            }

            //有表，或者创建好后,将对应时间的人数加1
            switch (str2) {
                case "00":
                    result.setOne(result.getOne()+1);
                    break;
                case "01":
                    result.setTwo(result.getTwo()+1);
                    break;
                case "02":
                    result.setThree(result.getThree()+1);
                    break;
                case "03":
                    result.setFour(result.getFour()+1);
                    break;
                case "04":
                    result.setFive(result.getFive()+1);
                    break;
                case "05":
                    result.setSix(result.getSix()+1);
                    break;
                case "06":
                    result.setSeven(result.getSeven()+1);
                    break;
                case "07":
                    result.setEight(result.getEight()+1);
                    break;
                case "08":
                    result.setNine(result.getNine()+1);
                    break;
                case "09":
                    result.setTen(result.getTen()+1);
                    break;
                case "10":
                    result.setEleven(result.getEleven()+1);
                    break;
                case "11":
                    result.setTwelve(result.getTwelve()+1);
                    break;
                case "12":
                    result.setThirteen(result.getThirteen()+1);
                    break;
                case "13":
                    result.setFourteen(result.getFourteen()+1);
                    break;
                case "14":
                    result.setFifteen(result.getFifteen()+1);
                    break;
                case "15":
                    result.setSixteen(result.getSixteen()+1);
                    break;
                case "16":
                    result.setSeventeen(result.getSeventeen()+1);
                    break;
                case "17":
                    result.setEighteen(result.getEighteen()+1);
                    break;
                case "18":
                    result.setNineteen(result.getNineteen()+1);
                    break;
                case "19":
                    result.setTwenty(result.getTwenty()+1);
                    break;
                case "20":
                    result.setTwentyone(result.getTwentyone()+1);
                    break;
                case "21":
                    result.setTwentytwo(result.getTwentytwo()+1);
                    break;
                case "22":
                    result.setTwentythree(result.getTwentythree()+1);
                    break;
                case "23":
                    result.setTwentyfour(result.getTwentyfour()+1);
                    break;
                default:
                    break;
            }

            detectionService.update(result);
            return "200";

        }else {
            return "-1";
        }

    }

    /**
     * 查询商场当天的实时人数。当天入人次 - 当天出人次
     * @return  返回人数数值
     */
    @GetMapping("/nowpeople")
    public int nowpeople() {
        Date d1 = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String str1 = sdf1.format(d1);     //日期
        List<Detection> resultIn = detectionService.SelectBydate(str1,"0");//查询记录
        List<Detection> resultOut = detectionService.SelectBydate(str1,"1");//查询记录
        int nowpeoples = 0;
        for(Detection s : resultIn){
            nowpeoples += s.sum();
        }
        for(Detection s : resultOut){
            nowpeoples -= s.sum();
        }
        return nowpeoples;

    }


    /**
     * 查询某一天每小时人次，只考虑入的人数，不考虑出的人数。
     * @param date   要查询的日期，格式：yyyy-MM-dd,不补零也可以：yyyy-M-d
     * @return    返回每个摄像头的人次和总的人次
     */
    @GetMapping("/perday/{date}")
    public Map<Integer,int[]> perDay(@PathVariable String date){
        List<Camera> cam = cameraService.getAll();  //查询有几个摄像头
        Map<Integer, int[]> result = new HashMap<Integer,int[]>();   //新建map集合用作返回
        //初始化
        for(Camera c : cam){
            int[] row = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            result.put(c.getId(),row);
        }
        int[] sumcam = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};   //新建数组存总人次,
        List<Detection> resultIn = detectionService.SelectBydate(date,"0");//查询记录
        for(Detection d : resultIn){
            result.replace(d.getCamera(),d.getAllData());
            for(int i=0; i<24; i++){
                sumcam[i] += d.getAllData()[i];      //计算总人次
            }
        }
        result.put(0,sumcam);           //将总人次加入集合,0表示是总人次

        return result;

    }

    /**
     * 查询商场某星期每天人次
     * @param yearweek 获取到的是年月日，返回的是该日及其后六天的数据
     * @return  返回每个摄像头的人次和总的人次
     */
    @GetMapping("/perweek/{yearweek}")
    public Map<Integer,int[]> perWeek(@PathVariable String yearweek){
        List<Camera> cam = cameraService.getAll();  //查询有几个摄像头
        Map<Integer, int[]> result = new HashMap<Integer,int[]>();   //新建map集合用作返回
        //初始化
        for(Camera c : cam){
            int[] row = new int[]{0,0,0,0,0,0,0};
            result.put(c.getId(),row);
        }
        int[] sum = new int[]{0,0,0,0,0,0,0};    //总人次
        result.put(0,sum);

        int year = Integer.valueOf(yearweek.substring(0,4));
        int month = Integer.valueOf(yearweek.substring(5,7));
        int day = Integer.valueOf(yearweek.substring(8,10));
        //获取指定的时间的日历对象
        LocalDate ldDate = LocalDate.of(year,month,day);

        for(int i=0; i<7;i++){
            LocalDate plusLocalDate = ldDate.plusDays(i);
            List<Detection> resultIn = detectionService.SelectBydate(plusLocalDate.toString(),"0");//查询记录
            int sumweek = 0;
            for(Detection s : resultIn){
                int[] row = result.get(s.getCamera());
                row[i] = s.sum();
                result.replace(s.getCamera(),row);
                sumweek += s.sum();
            }
            int[] row = result.get(0);
            row[i] = sumweek;
            result.replace(0,row);
        }

        return result;
    }


    /**
     * 查询某月每天的人次
     * @param yearmonth  月份从1开始计数,例如2023年一月：2023-01
     * @return   返回每个摄像头的人次和总的人次
     */
    @GetMapping("/permonth/{yearmonth}")
    public Map<Integer,int[]> perMonth(@PathVariable String yearmonth){
        List<Camera> cam = cameraService.getAll();  //查询有几个摄像头
        Map<Integer, int[]> result = new HashMap<Integer,int[]>();   //新建map集合用作返回
        //初始化
        int day[]=new int[] {31,28,31,30,31,30,31,31,30,31,30,31};//每月的天数不一样，二月按照29天算，不是闰年的话第29天补零
        int year = Integer.valueOf(yearmonth.substring(0,4));
        int month = Integer.valueOf(yearmonth.substring(5,7));
        for(Camera c : cam){
            int[] row = new int[day[month-1]];
            for(int i=0;i<row.length;i++){
                row[i]=0;
            }
            result.put(c.getId(),row);
        }
        int[] sum = new int[day[month-1]];    //总人数
        for(int i=0;i<sum.length;i++){
            sum[i]=0;
        }
        result.put(0,sum);

        //查
        for(int i=0; i<day[month-1];i++){
            List<Detection> resultIn = detectionService.SelectBydate(year+"-"+(month)+"-"+(i+1),"0");//查询记录
            int summonth = 0;
            for(Detection s : resultIn){
                int[] row = result.get(s.getCamera());
                row[i] = s.sum();
                result.replace(s.getCamera(),row);
                summonth += s.sum();
            }
            int[] row = result.get(0);
            row[i] = summonth;
            result.replace(0,row);
        }
        return result;
    }


    /**
     * 查询商场某年每月人次
     * @param year   年份
     * @return 返回每个摄像头的人次和总的人次
     */
    @GetMapping("/peryear/{year}")
    public Map<Integer,int[]> perYear(@PathVariable String year){
        List<Camera> cam = cameraService.getAll();  //查询有几个摄像头
        Map<Integer, int[]> result = new HashMap<Integer,int[]>();   //新建map集合用作返回
        int[] sum = new int[]{0,0,0,0,0,0,0,0,0,0,0,0};
        for(Camera c : cam){
            int[] row = new int[]{0,0,0,0,0,0,0,0,0,0,0,0};
            for(int i=0; i<12; i++){
                List<Detection> resultIn = detectionService.SelectByMonthCam(year+"-"+String.format("%02d", i+1),"0",c.getId()+"");    //月份不足两位要在前面补0
                int summonth=0;
                for(Detection s : resultIn){
                    summonth += s.sum();
                }
                row[i] = summonth;
                sum[i] += summonth;
            }
            result.put(c.getId(),row);
            result.put(0,sum);
        }
        return result;
                //detectionService.SelectByMonthCam(yearmonth,"0","1");
    }


    //分页查询
    @GetMapping("{currentPage}/{pageSize}")
    public IPage<Detection> getPage(@PathVariable int currentPage, @PathVariable int pageSize){
        return detectionService.getPage(currentPage,pageSize);
    }



    //返回累计总人数
    @GetMapping("/sum")
    public Integer sumAll(){
        List<Detection> resultIn = detectionService.getAll();
        int sum = 0;
        for(Detection s : resultIn){
            sum += s.sum();
        }

        return sum;

    }



    /**
     * java8 根据年份和周数获取周一或者周日日期
     * @param category 例如2021-01，代表2021年第一周
     * @param newValue 1L代表周一，7L代表周日
     * @return 日期 yyyy-MM-dd
     */
    public static LocalDate getLocalDateByCategory(String category, long newValue){
        int year = Integer.valueOf(category.substring(0,4));
        int numWeek = Integer.valueOf(category.substring(5,7));
        WeekFields weekFields= WeekFields.ISO;
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.withYear(year).with(weekFields.weekOfYear(),numWeek);
        LocalDate goalLocalDate  = localDate.with(weekFields.dayOfWeek(), newValue);
        return goalLocalDate;
    }




}
