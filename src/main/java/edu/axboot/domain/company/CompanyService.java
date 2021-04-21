package edu.axboot.domain.company;

import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;
import edu.axboot.domain.BaseService;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.chequer.axboot.core.parameter.RequestParams;
import java.util.List;

@Service
public class CompanyService extends BaseService<Company, Long> {
    private CompanyRepository companyRepository;

    @Inject //버전마다 어노테이션이 다를 수 있음.
    //mapper 사용하기 위한 선언, mapper는 별도로 bean으로 인젝션을 시켜줘야함.
    private CompanyMapper companyMapper;

    @Inject
    public CompanyService(CompanyRepository companyRepository) {
        super(companyRepository);
        this.companyRepository = companyRepository;
    }

    public List<Company> gets(RequestParams<Company> requestParams) {
        return findAll();
    }

    //QueryDSL
    public List<Company> getByQueryDsl(RequestParams<Company> requestParams) {
        String company = requestParams.getString("company", "");
        String ceo = requestParams.getString("ceo", "");
        String bizno = requestParams.getString("bizno", "");

        BooleanBuilder builder = new BooleanBuilder();
        if (isNotEmpty(company)) {
            builder.and(qCompany.companyNm.eq(company));
        }
        if (isNotEmpty(ceo)) {
            builder.and(qCompany.ceo.eq(ceo));
        }
        if (isNotEmpty(bizno)) {
            builder.and(qCompany.bizno.eq(bizno));
        }
        List<Company> companyList = select()
                .from(qCompany)
                .where(builder)
                .orderBy(qCompany.companyNm.asc())
                .fetch();
        return companyList;
    }

    @Transactional
    public void saveByQueryDsl(List<Company> request) {
        for (Company company: request) {
            if (company.isCreated()) {
                save(company);
            } else if (company.isModified()) {
                update(qCompany)
                        .set(qCompany.companyNm, company.getCompanyNm())
                        .set(qCompany.ceo, company.getCeo())
                        .where(qCompany.id.eq(company.getId()))
                        .execute();
            } else if (company.isDeleted()) {
                delete(qCompany)
                        .where(qCompany.id.eq(company.getId()))
                        .execute();
            }
        }
    }

    //selectBy new메서드 생성
    public List<Company> selectBy(RequestParams<Company> requestParams) {

        // 받을 파라미터값 추가
        /*
            String company = requestParams.getString("company", "");
            String ceo = requestParams.getString("ceo", "");
            String bizno = requestParams.getString("bizno", "");
            */

        //company를 담을 객체를 생성
        Company company = new Company();
        company.setCompanyNm(requestParams.getString("company", ""));
        company.setCeo(requestParams.getString("ceo", ""));
        company.setBizno(requestParams.getString("bizno", ""));


        //companyMapper에 selectBy를 생성.
        // List<Company> companyList = this.companyMapper.selectBy("company", ""); //회사명으로 검색해서 데이터를 가져옴
          return this.companyMapper.selectBy(company);
//        return this.companyMapper.selectBy(requestParams.getString("company", "")); // 만약 mapper에 parameter가 String으로 되어 있었다면
    }

    //결과값이 나갈 필요가 없어서 void
    public void saveByMyBatis(List<Company> request) {
        for (Company company: request) {
            if (company.isCreated()) {
                this.companyMapper.insert(company);
            } else if (company.isModified()) {
                this.companyMapper.update(company);
            } else if (company.isDeleted()) {
                this.companyMapper.delete(company);
            }
        }
    }

    public Company selectOne(RequestParams<Company> requestParams) {
        return this.companyMapper.selectOne(requestParams.getLong("id", 0));
    }
}