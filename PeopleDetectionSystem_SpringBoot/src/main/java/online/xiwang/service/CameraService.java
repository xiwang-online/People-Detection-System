package online.xiwang.service;

import online.xiwang.dao.CameraDao;
import online.xiwang.domain.Camera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CameraService {
    @Autowired
    CameraDao cameraDao;

    //获取全部
    public List<Camera> getAll() {
        return cameraDao.selectList(null);
    }


    //新增
    public Boolean save(Camera camera){
        return cameraDao.insert(camera)>0;

    }

    //修改
    public Boolean update(Camera camera){
        return cameraDao.updateById(camera) > 0;
    }

    //删除
    public Boolean delete(Integer id) {
        return  cameraDao.deleteById(id) > 0;
    }

    //通过id查找
    public Camera getbyid(Integer id) {
        return  cameraDao.selectById(id);
    }




}
