package edu.axboot.domain.education;

import com.querydsl.core.BooleanBuilder;
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
    private NhmGridMapper nhmGridMapper;

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
        String useYn = requestParams.getString("useYn", "");

        List<NhmGrid> nhmGridList = this.getByQueryDsl(company, ceo, bizno, useYn);

        return nhmGridList;
    }
    public List<NhmGrid> getByQueryDsl(String companyNm, String ceo, String bizno, String useYn) {
        BooleanBuilder builder = new BooleanBuilder();
        if(isNotEmpty(companyNm)) builder.and(qNhmGrid.companyNm.contains(companyNm));
        if(isNotEmpty(ceo)) builder.and(qNhmGrid.ceo.like(ceo + "%"));
        if(isNotEmpty(bizno)) builder.and(qNhmGrid.bizno.contains(bizno));
        if(isNotEmpty(useYn)) builder.and(qNhmGrid.useYn.eq(useYn));

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

    public List<NhmGrid> getByMyBatis(RequestParams<NhmGrid> requestParams) {
        NhmGrid nhmGrid = new NhmGrid();
        nhmGrid.setCompanyNm(requestParams.getString("company", ""));
        nhmGrid.setCeo(requestParams.getString("ceo", ""));
        nhmGrid.setBizno(requestParams.getString("bizno", ""));
        nhmGrid.setUseYn(requestParams.getString("useYn", ""));

        return this.nhmGridMapper.selectBy(nhmGrid);
    }


    public NhmGrid getOneByMyBatis(long id) {
        return this.nhmGridMapper.selectOne(id);
    }

    public void saveByMyBatis(List<NhmGrid> request) {
        for (NhmGrid nhmGrid : request){
            if (nhmGrid.isCreated()) {
                this.nhmGridMapper.insert(nhmGrid);
            } else if (nhmGrid.isModified()) {
                this.nhmGridMapper.update(nhmGrid);
            } else if (nhmGrid.isDeleted()) {
                this.nhmGridMapper.delete(nhmGrid);
            }
        }
    }
}