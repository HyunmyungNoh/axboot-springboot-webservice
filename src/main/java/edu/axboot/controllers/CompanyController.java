package edu.axboot.controllers;

import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.controllers.BaseController;
import com.chequer.axboot.core.parameter.RequestParams;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import org.springframework.stereotype.Controller;
import com.chequer.axboot.core.api.response.ApiResponse;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import edu.axboot.domain.company.Company;
import edu.axboot.domain.company.CompanyService;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/company")
public class CompanyController extends BaseController {

    @Inject
    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON)
    public Responses.ListResponse list(RequestParams<Company> requestParams) {
        List<Company> list = companyService.gets(requestParams);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(method = {RequestMethod.PUT}, produces = APPLICATION_JSON)
    public ApiResponse save(@RequestBody List<Company> request) {
        companyService.save(request);
        return ok();
    }

    @RequestMapping(value = "/QueryDsl", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "회사명", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ceo", value = "대표자", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bizno", value = "사업자번호", dataType = "String", paramType = "query")
    })
    public Responses.ListResponse list2(RequestParams<Company> requestParams) {
        List<Company> list = companyService.getByQueryDsl(requestParams);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(value = "/QueryDsl", method = {RequestMethod.PUT}, produces = APPLICATION_JSON)
    public ApiResponse save2(@RequestBody List<Company> request) {
        companyService.saveByQueryDsl(request);
        return ok();
    }

    @RequestMapping(value = "/MyBatis", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "회사명", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ceo", value = "대표자", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bizno", value = "사업자번호", dataType = "String", paramType = "query")
    })
    public Responses.ListResponse list3(RequestParams<Company> requestParams) {
        List<Company> list = companyService.selectBy(requestParams);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(value = "/MyBatis", method = {RequestMethod.PUT}, produces = APPLICATION_JSON)
    public ApiResponse save3(@RequestBody List<Company> request) {
        companyService.saveByMyBatis(request);
        return ok();
    }

    // 한 건 가져오기
    @RequestMapping(value = "/MyBatis/View", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "Long", paramType = "query")
    })
    // 유일한 하나의 객체를 가져오기 때문에 리턴 타입은 리스트가 아니다.
    public Company view3(RequestParams<Company> requestParams) {
        Company company = companyService.selectOne(requestParams);
        return company;
    }
}