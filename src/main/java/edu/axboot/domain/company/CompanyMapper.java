package edu.axboot.domain.company;

import com.chequer.axboot.core.mybatis.MyBatisMapper;

import java.util.List;

public interface CompanyMapper extends MyBatisMapper {
    List<Company> selectBy(Company company);
//    List<Company> selectBy(String companyName);

    void insert(Company company);
    void update(Company company);
    void delete(Company company);

    Company selectOne(long id);
}
