package zzu.example.trainpartnerproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import zzu.example.trainpartnerproject.entity.Partner;

@Mapper
public interface PartnerMapper extends BaseMapper<Partner> { // 补全泛型
}