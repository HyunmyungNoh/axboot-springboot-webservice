package edu.axboot.controllers;

import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.controllers.BaseController;
import com.chequer.axboot.core.parameter.RequestParams;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import edu.axboot.domain.company.Company;
import org.springframework.stereotype.Controller;
import com.chequer.axboot.core.api.response.ApiResponse;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import edu.axboot.domain.education.NhmGrid;
import edu.axboot.domain.education.NhmGridService;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/nhmGrid")
public class NhmGridController extends BaseController {

    @Inject
    private NhmGridService nhmGridService;

    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON)
    public Responses.ListResponse list(RequestParams<NhmGrid> requestParams) {
        List<NhmGrid> list = nhmGridService.gets(requestParams);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(method = {RequestMethod.PUT}, produces = APPLICATION_JSON)
    public ApiResponse save(@RequestBody List<NhmGrid> request) {
        nhmGridService.save(request);
        return ok();
    }

    // QueryDsl
    @RequestMapping(value = "/queryDsl", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "회사명", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ceo", value = "대표자", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bizno", value = "사업자번호", dataType = "String", paramType = "query")
    })
    public Responses.ListResponse listQueryDsl(RequestParams<NhmGrid> requestParams) {
        List<NhmGrid> list = nhmGridService.getByQueryDsl(requestParams);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(value = "/queryDslOne", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query")
    })
    public NhmGrid selectOne(RequestParams<Company> requestParams) {
//    public Company selectOne(@RequestParam("id") long id) {
        long id = requestParams.getLong("id", 0);

        NhmGrid nhmGrid = nhmGridService.getOneByQueryDsl(id);
        return nhmGrid;
    }

    @RequestMapping(value = "/queryDsl", method = {RequestMethod.PUT}, produces = APPLICATION_JSON)
    public ApiResponse saveQueryDsl(@RequestBody List<NhmGrid> request) {
        nhmGridService.saveByQueryDsl(request);
        return ok();
    }
}