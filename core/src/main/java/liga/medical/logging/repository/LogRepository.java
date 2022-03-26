package liga.medical.logging.repository;

import liga.medical.logging.model.LogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface LogRepository {

    @Insert("insert into logs (type, data_and_time, name_method, full_name_class, name_user) " +
            "values(#{type}, #{dateTime}, #{nameMethod}, #{fullNameClass}, #{nameUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(LogEntity logEntity);
}
