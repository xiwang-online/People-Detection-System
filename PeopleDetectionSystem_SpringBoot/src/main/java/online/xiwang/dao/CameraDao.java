package online.xiwang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.xiwang.domain.Camera;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CameraDao extends BaseMapper<Camera> {
}