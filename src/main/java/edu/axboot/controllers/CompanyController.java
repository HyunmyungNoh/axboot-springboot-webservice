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
import org.springframework.web.bind.annotation.RequestParam;

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

    // QueryDsl 이용
    @RequestMapping(value = "/queryDsl", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "회사명", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ceo", value = "대표자", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bizno", value = "사업자번호", dataType = "String", paramType = "query")
    })
    public Responses.ListResponse list2(RequestParams<Company> requestParams) {
        List<Company> list = companyService.getByQueryDsl(requestParams);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(value = "/queryDsl", method = {RequestMethod.PUT}, produces = APPLICATION_JSON)
    public ApiResponse save2(@RequestBody List<Company> request) {
        companyService.saveByQueryDsl(request);
        return ok();
    }

    /*한 건 다루기*/
    // 한 건 조회
    @RequestMapping(value = "/queryDsl/view", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "Long", paramType = "query")
    })
    // 유일한 하나의 객체를 가져오기 때문에 리턴 타입은 리스트가 아니다.
    // 원래 어떤 Object를 가져오지 않고 string이나 int 등 하나의 인자만 가져오면 RequestParam으로 직접 가져온다.
    public Company selectOne(RequestParams<Company> requestParams) {
//    public Company selectOne(@RequestParam("id") Long id) {
        Long id = requestParams.getLong("id", 0);

        Company company = companyService.getOneByQueryDsl(id);
        return company;
    }

    // 한 건 save
    @RequestMapping(value = "/queryDsl/save", method = {RequestMethod.PUT}, produces = APPLICATION_JSON)
    public ApiResponse saveOne(@RequestBody Company request) {
        companyService.saveOneByQueryDsl(request);
        return ok();
    }

   /* @RequestMapping(value = "/MyBatis", method = RequestMethod.GET, produces = APPLICATION_JSON)
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
    public Company selectOne3(RequestParams<Company> requestParams) {
        Company company = companyService.selectOne(requestParams);
        return company;
    }*/

}