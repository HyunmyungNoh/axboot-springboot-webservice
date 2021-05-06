package edu.axboot.domain.education2;

import com.chequer.axboot.core.parameter.RequestParams;
import com.querydsl.core.BooleanBuilder;
import edu.axboot.domain.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class EducationNhmService extends BaseService<EducationNhm, Long> {
    // logger 사용
    private static final Logger logger = LoggerFactory.getLogger(EducationNhmService.class);

    private EducationNhmRepository educationNhmRepository;

    @Inject
    private EducationNhmMapper educationNhmMapper;

    @Inject
    public EducationNhmService(EducationNhmRepository educationNhmRepository) {
        super(educationNhmRepository);
        this.educationNhmRepository = educationNhmRepository;
    }
/*
    public List<EducationNhm> gets(RequestParams<EducationNhm> requestParams) {
        return findAll();
    }*/

    // QueryDsl
    public List<EducationNhm> getByQueryDsl(RequestParams<EducationNhm> requestParams) {
        String companyNm = requestParams.getString("companyNm", "");
        String ceo = requestParams.getString("ceo", "");
        String bizno = requestParams.getString("bizno", "");
        String useYn = requestParams.getString("useYn", "");
        logger.info("회사명 : " + companyNm);
        logger.info("대표자 : " + ceo);
        logger.info("사업자번호 : " + bizno);
        logger.info("사용여부 : " + (useYn == "Y"? "사용함": "사용안함"));

        List<EducationNhm> educationNhmList = this.getByQueryDsl(companyNm, ceo, bizno, useYn);

        return educationNhmList;
    }
    public List<EducationNhm> getByQueryDsl(String companyNm, String ceo, String bizno, String useYn) {
        BooleanBuilder builder = new BooleanBuilder();
        if(isNotEmpty(companyNm)) builder.and(qEducationNhm.companyNm.contains(companyNm));
        if(isNotEmpty(ceo)) builder.and(qEducationNhm.ceo.like(ceo + "%"));
        if(isNotEmpty(bizno)) builder.and(qEducationNhm.bizno.eq(bizno));
        if(isNotEmpty(useYn)) builder.and(qEducationNhm.useYn.eq(useYn));

        List<EducationNhm> educationNhmList = select().
                from(qEducationNhm).
                where(builder).
                orderBy(qEducationNhm.companyNm.asc())
                .fetch();

        return educationNhmList;
    }

    public EducationNhm getOneByQueryDsl(long id) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qEducationNhm.id.eq(id));

        EducationNhm educationNhm = select()
                .from(qEducationNhm)
                .where(builder)
                .fetchOne();

        return educationNhm;
    }

    @Transactional
    public long saveByQueryDsl(List<EducationNhm> request) {
        long result = 0;

        for (EducationNhm educationNhm : request){
            if (educationNhm.isCreated()) {
                EducationNhm rtnObj = save(educationNhm);
                result = rtnObj.getId();
            } else if (educationNhm.isModified()) {
                result = update(qEducationNhm)
                        .set(qEducationNhm.companyNm, educationNhm.getCompanyNm())
                        .set(qEducationNhm.ceo, educationNhm.getCeo())
                        .set(qEducationNhm.bizno, educationNhm.getBizno())
                        .set(qEducationNhm.tel, educationNhm.getTel())
                        .set(qEducationNhm.email, educationNhm.getEmail())
                        .set(qEducationNhm.useYn, educationNhm.getUseYn())
                        .where(qEducationNhm.id.eq(educationNhm.getId()))
                        .execute();
            } else if (educationNhm.isDeleted()) {
                result = delete(qEducationNhm)
                        .where(qEducationNhm.id.eq(educationNhm.getId()))
                        .execute();
            }
        }
        return result;
    }

    // MyBatis
/*    public List<EducationNhm> getByMyBatis(RequestParams<EducationNhm> requestParams) {
        EducationNhm educationNhm = new EducationNhm();
        educationNhm.setCompanyNm(requestParams.getString("companyNm", ""));
        educationNhm.setCeo(requestParams.getString("ceo", ""));
        educationNhm.setBizno(requestParams.getString("bizno", ""));
        educationNhm.setUseYn(requestParams.getString("useYn", ""));

        return this.educationNhmMapper.selectBy(educationNhm);
    }


    public EducationNhm getOneByMyBatis(long id) {
        return this.educationNhmMapper.selectOne(id);
    }

    public void saveByMyBatis(List<EducationNhm> request) {
        for (EducationNhm educationNhm : request){
            if (educationNhm.isCreated()) {
                this.educationNhmMapper.insert(educationNhm);
            } else if (educationNhm.isModified()) {
                this.educationNhmMapper.update(educationNhm);
            } else if (educationNhm.isDeleted()) {
                this.educationNhmMapper.delete(educationNhm);
            }
        }
    }*/

    /********
     * 4/28 Form을 위한 새로운 서비스 함수
     * ********/

    /*JPA*/
    public List<EducationNhm> gets(RequestParams<EducationNhm> requestParams) {
        List<EducationNhm> list = this.getFilter(findAll(), requestParams.getString("companyNm", ""), 1);
        List<EducationNhm> list2 = this.getFilter(list, requestParams.getString("ceo", ""), 2);
        List<EducationNhm> list3 = this.getFilter(list2, requestParams.getString("bizno", ""), 3);
        List<EducationNhm> list4 = this.getFilter(list3, requestParams.getString("useYn", ""), 4);

        return list4;
    }

    // 조건에 맞는 것을 찾는 함수
    private List<EducationNhm> getFilter(List<EducationNhm> sources, String filter, int typ) {
        List<EducationNhm> targets = new ArrayList<EducationNhm>();
        for (EducationNhm entity: sources) {
            if ("".equals(filter)) {
                targets.add(entity);
            } else {
                if (typ == 1) {
                    if (entity.getCompanyNm().contains(filter)) {
                        targets.add(entity);
                    }
                } else if (typ == 2) {
                    if (entity.getCeo().contains(filter)) {
                        targets.add(entity);
                    }
                } else if (typ == 3) {
                    if (entity.getBizno().equals(filter)) {
                        targets.add(entity);
                    }
                } else if (typ == 4) {
                    if (entity.getUseYn().equals(filter)) {
                        targets.add(entity);
                    }
                }
            }
        }
        return targets;
    }

    /*QueryDsl*/
    public List<EducationNhm> gets(String companyNm, String ceo, String bizno, String useYn) {
        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(companyNm)) builder.and(qEducationNhm.companyNm.like("%" + companyNm + "%"));
        if (isNotEmpty(ceo)) builder.and(qEducationNhm.ceo.like("%" + ceo +"%"));
        if (isNotEmpty(bizno)) builder.and(qEducationNhm.bizno.like("%" + bizno + "%"));
        if (isNotEmpty(useYn)) builder.and(qEducationNhm.useYn.eq(useYn));

        List<EducationNhm> list = select()
                .from(qEducationNhm)
                .where(builder)
                .orderBy(qEducationNhm.companyNm.asc())
                .fetch();

        return list;
    }

    public EducationNhm getByOne(Long id) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qEducationNhm.id.eq(id));

        EducationNhm entity = select()
                .from(qEducationNhm)
                .where(builder)
                .fetchOne();

        return entity;
    }

    @Transactional
    public void persist(EducationNhm request) {
        if (request.getId() == null || request.getId() == 0) {
            save(request);
        } else {
            update(qEducationNhm)
                    .set(qEducationNhm.companyNm, request.getCompanyNm())
                    .set(qEducationNhm.ceo, request.getCeo())
                    .set(qEducationNhm.bizno, request.getBizno())
                    .set(qEducationNhm.tel, request.getTel())
                    .set(qEducationNhm.zip, request.getZip())
                    .set(qEducationNhm.address, request.getAddress())
                    .set(qEducationNhm.addressDetail, request.getAddressDetail())
                    .set(qEducationNhm.email, request.getEmail())
                    .set(qEducationNhm.remark, request.getRemark())
                    .set(qEducationNhm.useYn, request.getUseYn())
                    .where(qEducationNhm.id.eq(request.getId()))
                    .execute();
        }
    }

    @Transactional
    public void remove(Long id) {
        delete(qEducationNhm)
        .where(qEducationNhm.id.eq(id))
        .execute();
    }

    /*MyBatis*/
    public List<EducationNhm> select(String companyNm, String ceo, String bizno, String useYn) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("companyNm", companyNm);
        params.put("ceo", ceo);
        params.put("bizno", bizno);
        params.put("useYn", useYn);

        List<EducationNhm> list = educationNhmMapper.select(params);
        return list;
    }

    public EducationNhm selectOne (Long id) { return educationNhmMapper.selectOne(id);}

    public void enroll(EducationNhm request) {
        if (request.getId() == null || request.getId() == 0) {
            educationNhmMapper.insert(request);
        } else {
            educationNhmMapper.update(request);
        }
    }

    public void del(Long id) { educationNhmMapper.delete(id); }

    /* 예외 처리용 함수 */
    public List<EducationNhm> getListUsingMyBatis(RequestParams<EducationNhm> requestParams) {
        String companyNm = requestParams.getString("companyNm", "");
        String ceo = requestParams.getString("ceo", "");
        String bizno = requestParams.getString("bizno", "");
        String useYn = requestParams.getString("useYn", "");

        if (!"".equals(useYn) && !"Y".equals(useYn) && !"N".equals(useYn)) {
            throw new RuntimeException("Y 아니면 N 입력하세요~");
        }

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("companyNm", companyNm);
        params.put("ceo", ceo);
        params.put("bizno", bizno);
        params.put("useYn", useYn);

        List<EducationNhm> list = educationNhmMapper.select(params);

        return list;
    }
}