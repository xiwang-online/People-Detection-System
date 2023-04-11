package online.xiwang.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.xiwang.dao.UserDao;
import online.xiwang.domain.Camera;
import online.xiwang.domain.Detection;
import online.xiwang.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;


    //通过用户名查找
    public User getuser(String name){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        User result = userDao.selectOne(queryWrapper);
        return result;

    }

    //获取全部
    public List<User> getAll() {
        return userDao.selectList(null);
    }


    //新增
    public Boolean save(User user){
        return userDao.insert(user)>0;

    }

    //修改
    public Boolean update(User user){
        return userDao.updateById(user) > 0;
    }

    //删除
    public Boolean delete(Integer id) {
        return  userDao.deleteById(id) > 0;
    }

    public User getbyid(Integer id) {
        return  userDao.selectById(id);
    }
}
