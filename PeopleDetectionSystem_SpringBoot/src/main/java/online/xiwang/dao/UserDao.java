package online.xiwang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.xiwang.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
