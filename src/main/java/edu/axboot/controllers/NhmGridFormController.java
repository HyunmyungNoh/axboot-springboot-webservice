package edu.axboot.controllers;

import com.chequer.axboot.core.api.response.ApiResponse;
import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.controllers.BaseController;
import com.chequer.axboot.core.parameter.RequestParams;
import edu.axboot.domain.education.EducationNhm;
import edu.axboot.domain.education.EducationNhmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/education/nhmGridForm")
public class NhmGridFormController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(NhmGridFormController.class);

    @Inject
    private EducationNhmService educationNhmService;

    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON)
    public Responses.ListResponse list(@RequestParam(value = "companyNm", required = false) String companyNm,
                                       @RequestParam(value = "ceo", required = false) String ceo,
                                       @RequestParam(value = "bizno", required = false) String bizno,
                                       @RequestParam(value = "useYn", required = false) String useYn) {
        List<EducationNhm> list = educationNhmService.gets(companyNm, ceo, bizno, useYn);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public EducationNhm view(@PathVariable Long id) {
        EducationNhm entity = educationNhmService.getByOne(id);

        return entity;
    }

    @RequestMapping(method = {RequestMethod.POST}, produces = APPLICATION_JSON)
    public ApiResponse save(@RequestBody EducationNhm request) {
        educationNhmService.persist(request);
        return ok();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON)
    public ApiResponse remove(@PathVariable Long id) {
        educationNhmService.remove(id);
        return ok();
    }

    /* 예외처리용 함수 try~catch 사용 */
    @RequestMapping(value="/mybatis/exception", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public Responses.ListResponse listByMyBatis(@RequestParam(value = "companyNm", required = false) String companyNm,
                                                @RequestParam(value = "ceo", required = false) String ceo,
                                                @RequestParam(value = "bizno", required = false) String bizno,
                                                @RequestParam(value = "useYn", required = false) String useYn) {
        try {
            RequestParams<EducationNhm> requestParams = new RequestParams<>();
            requestParams.put("companyNm", companyNm);
            requestParams.put("ceo", ceo);
            requestParams.put("bizno", bizno);
            requestParams.put("useYn", useYn);
            List<EducationNhm> list = educationNhmService.getListUsingMyBatis(requestParams);
            return Responses.ListResponse.of(list);
        } catch (Exception e) {
            logger.error("마이바티스 조회 오류. 쿼리 확인해 보세요~");
            return Responses.ListResponse.of(null);
        }
    }

}