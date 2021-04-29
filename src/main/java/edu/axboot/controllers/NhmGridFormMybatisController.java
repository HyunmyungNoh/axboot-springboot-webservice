package edu.axboot.controllers;

import com.chequer.axboot.core.api.response.ApiResponse;
import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.controllers.BaseController;
import edu.axboot.domain.education.EducationNhm;
import edu.axboot.domain.education.EducationNhmService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/******** 4/28 Form을 위한 MyBatis controller ********/

@Controller
@RequestMapping(value = "/api/v1/education/nhmGridForm/myBatis")
public class NhmGridFormMybatisController extends BaseController {

    @Inject
    private EducationNhmService educationNhmService;

    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON)
    public Responses.ListResponse list(@RequestParam(value= "companyNm", required=false) String companyNm,
                                       @RequestParam(value = "ceo", required = false) String ceo,
                                       @RequestParam(value = "bizno", required = false) String bizno,
                                       @RequestParam(value = "useYn", required = false) String useYn) {
        List<EducationNhm> list = educationNhmService.select(companyNm, ceo, bizno, useYn);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public EducationNhm view(@PathVariable Long id) {
        EducationNhm entity = educationNhmService.selectOne(id);

        return entity;
    }

    @RequestMapping(method = RequestMethod.POST, produces = APPLICATION_JSON)
    public ApiResponse save(@RequestBody EducationNhm request) {
        educationNhmService.enroll(request);
        return ok();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON)
    public ApiResponse remove(@PathVariable Long id) {
        educationNhmService.del(id);
        return ok();
    }
}