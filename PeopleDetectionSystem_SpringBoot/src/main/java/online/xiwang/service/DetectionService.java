package online.xiwang.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.xiwang.dao.DetectionDao;
import online.xiwang.domain.Detection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetectionService {
    @Autowired
    DetectionDao detectionDao;

    //获取全部
    public List<Detection> getAll() {
        return detectionDao.selectList(null);
    }

    //新增
    public Boolean save(Detection detection) {
        return detectionDao.insert(detection) > 0;
    }

    //按照日期、摄像头和出入查询
    public Detection SelectBydateCam(String date,String inout1,String cam){
        QueryWrapper<Detection> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("date", date)
                .eq("inout1",inout1)
                .eq("camera",cam);
        Detection result = detectionDao.selectOne(queryWrapper);    //只查到一条记录
        return result;
    }

    //按照日期、出入查询
    public List<Detection> SelectBydate(String date,String inout1){
        QueryWrapper<Detection> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("date", date)
                .eq("inout1",inout1);
        List<Detection> result = detectionDao.selectList(queryWrapper);
        return result;
    }


    //按照月份、出入、摄像头查询，查到的是某月某个摄像头的全部记录
    public List<Detection> SelectByMonthCam(String yearmonth, String inout1,String cam){    //yearmonth格式：2023-04
        List<Detection> result = detectionDao.selectDecByMonth(yearmonth+"-01",yearmonth+"-31",inout1, cam);
        return result;

    }


    //修改
    public Boolean update(Detection detection){
        return detectionDao.updateById(detection) > 0;

    }



    //分页展示
    public IPage<Detection> getPage(int currentPage, int pageSize) {
        IPage page = new Page(currentPage,pageSize);
        detectionDao.selectPage(page,null);
        return page;
    }
}
