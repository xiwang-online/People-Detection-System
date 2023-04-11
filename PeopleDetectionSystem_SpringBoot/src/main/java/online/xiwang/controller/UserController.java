package online.xiwang.controller;

import online.xiwang.domain.Camera;
import online.xiwang.domain.User;
import online.xiwang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController   //返回值为json格式或者文本格式
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;


    //登录
    @PostMapping("/login")
    public Map<String,String> lognin(HttpServletRequest request, @RequestBody Map<String, String> map){
        String name = map.get("name");
        String password = map.get("password");
        Map<String, String> result = new HashMap<String, String>();   //新建map集合用作返回
        User user = userService.getuser(name);
        if(user != null){
            if(user.getPassword().equals(password)){
                request.getSession().setAttribute("usertype", user.getUsertype());
                result.put("status","200");
                result.put("name",name);
                result.put("usertype",user.getUsertype());
                result.put("remark", user.getRemark());
                result.put("avatar", user.getAvatar());
                result.put("phone", user.getPhone());
                return result;
            }else{
                result.put("status","1");
                result.put("msr","密码错误");
                return result;
            }
        }else{
            result.put("status","2");
            result.put("msr","用户名不存在");
            return result;
        }
    }





    //获取全部
    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    //新增
    @PostMapping("/save")
    public Boolean save(@RequestBody User user){
        return userService.save(user);
    }

    //修改
    @PostMapping("/update")
    public Boolean update(@RequestBody User user){
        return userService.update(user);
    }

    //删除
    @PostMapping("/delete/{id}")
    public  Boolean delete(@PathVariable Integer id){
        return  userService.delete(id);
    }

    @GetMapping("{id}")
    public User getById(@PathVariable Integer id){
        return userService.getbyid(id);

    }

}
