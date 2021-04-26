package edu.axboot.domain.education;

import com.querydsl.core.BooleanBuilder;
import edu.axboot.domain.company.Company;
import org.springframework.stereotype.Service;
import edu.axboot.domain.BaseService;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.chequer.axboot.core.parameter.RequestParams;
import java.util.List;

@Service
public class NhmGridService extends BaseService<NhmGrid, Long> {
    private NhmGridRepository nhmGridRepository;

    @Inject
    public NhmGridService(NhmGridRepository nhmGridRepository) {
        super(nhmGridRepository);
        this.nhmGridRepository = nhmGridRepository;
    }

    public List<NhmGrid> gets(RequestParams<NhmGrid> requestParams) {
        return findAll();
    }

    // QueryDsl

    public List<NhmGrid> getByQueryDsl(RequestParams<NhmGrid> requestParams) {
        String company = requestParams.getString("company", "");
        String ceo = requestParams.getString("ceo", "");
        String bizno = requestParams.getString("bizno", "");

        List<NhmGrid> nhmGridList = this.getByQueryDsl(company, ceo, bizno);

        return nhmGridList;
    }
    public List<NhmGrid> getByQueryDsl(String companyNm, String ceo, String bizno) {
        BooleanBuilder builder = new BooleanBuilder();
        if(isNotEmpty(companyNm)) builder.and(qNhmGrid.companyNm.contains(companyNm));
        if(isNotEmpty(ceo)) builder.and(qNhmGrid.ceo.contains(ceo));
        if(isNotEmpty(bizno)) builder.and(qNhmGrid.companyNm.contains(bizno));

        List<NhmGrid> nhmGridList = select().
                from(qNhmGrid).
                where(builder).
                orderBy(qNhmGrid.companyNm.asc())
                .fetch();

        return nhmGridList;
    }

    public NhmGrid getOneByQueryDsl(long id) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qNhmGrid.id.eq(id));

        NhmGrid nhmGrid = select()
                .from(qNhmGrid)
                .where(builder)
                .fetchOne();

        return nhmGrid;
    }

    @Transactional
    public long saveByQueryDsl(List<NhmGrid> request) {
        long result = 0;

        for (NhmGrid nhmGrid: request){
            if (nhmGrid.isCreated()) {
                NhmGrid rtnObj = save(nhmGrid);
                result = rtnObj.getId();
            } else if (nhmGrid.isModified()) {
                result = update(qNhmGrid)
                        .set(qNhmGrid.companyNm, nhmGrid.getCompanyNm())
                        .set(qNhmGrid.ceo, nhmGrid.getCeo())
                        .set(qNhmGrid.bizno, nhmGrid.getBizno())
                        .set(qNhmGrid.tel, nhmGrid.getTel())
                        .set(qNhmGrid.email, nhmGrid.getEmail())
                        .set(qNhmGrid.useYn, nhmGrid.getUseYn())
                        .where(qNhmGrid.id.eq(nhmGrid.getId()))
                        .execute();
            } else if (nhmGrid.isDeleted()) {
                result = delete(qNhmGrid)
                        .where(qNhmGrid.id.eq(nhmGrid.getId()))
                        .execute();
            }
        }
        return result;
    }
}