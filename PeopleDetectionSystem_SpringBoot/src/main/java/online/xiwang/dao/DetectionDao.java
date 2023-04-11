package online.xiwang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.xiwang.domain.Detection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DetectionDao extends BaseMapper<Detection> {
    @Select("SELECT * FROM detection WHERE date >= #{yearmonthstart} AND date <= #{yearmonthend} AND inout1 = #{inout1} AND camera = #{cam}; ")
    List<Detection> selectDecByMonth(@Param("yearmonthstart") String yearmonthstart, @Param("yearmonthend") String yearmonthend, @Param("inout1") String inout1, @Param("cam") String cam);

}
