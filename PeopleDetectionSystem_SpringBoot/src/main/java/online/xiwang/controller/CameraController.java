package online.xiwang.controller;

import online.xiwang.domain.Camera;
import online.xiwang.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController   //返回值为json格式或者文本格式
@RequestMapping("/api/camera")
public class CameraController {
    @Autowired
    private CameraService cameraService;

    //获取全部
    @GetMapping
    public List<Camera> getAll(){
        return cameraService.getAll();
    }

    //新增
    @PostMapping("/save")
    public Boolean save(@RequestBody Camera camera){
        return cameraService.save(camera);
    }

    //修改
    @PostMapping("/update")
    public Boolean update(@RequestBody Camera camera){
        return cameraService.update(camera);
    }

    //删除
    @PostMapping("/delete/{id}")
    public  Boolean delete(@PathVariable Integer id){
        return  cameraService.delete(id);
    }

    @GetMapping("{id}")
    public Camera getById(@PathVariable Integer id){
        return cameraService.getbyid(id);

    }







}
