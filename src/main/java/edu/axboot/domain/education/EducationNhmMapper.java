package edu.axboot.domain.education;

import com.chequer.axboot.core.mybatis.MyBatisMapper;

import java.util.HashMap;
import java.util.List;


public interface EducationNhmMapper extends MyBatisMapper {

/*    List<EducationNhm> selectBy(EducationNhm educationNhm);
    EducationNhm selectOne(long id);

    void insert(EducationNhm educationNhm);
    void update(EducationNhm educationNhm);
    void delete(EducationNhm educationNhm);*/

    /* 4/28 form 용 mapper*/
    List<EducationNhm> select(HashMap<String, String> params);
    EducationNhm selectOne(Long id);

    int insert(EducationNhm educationNhm);
    int update(EducationNhm educationNhm);
    int delete(Long id);
}
