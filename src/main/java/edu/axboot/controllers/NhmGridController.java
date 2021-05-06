package edu.axboot.controllers;

import com.chequer.axboot.core.api.response.ApiResponse;
import com.chequer.axboot.core.api.response.Responses;
import com.chequer.axboot.core.controllers.BaseController;
import com.chequer.axboot.core.parameter.RequestParams;
import com.chequer.axboot.core.utils.DateUtils;
import com.chequer.axboot.core.utils.ExcelUtils;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import edu.axboot.domain.education2.EducationNhm;
import edu.axboot.domain.education2.EducationNhmService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/nhmGrid")
public class NhmGridController extends BaseController {

    @Inject
    private EducationNhmService educationNhmService;

    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyNm", value = "회사명", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ceo", value = "대표자", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bizno", value = "사업자번호", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "useYn", value = "사용여부", dataType = "String", paramType = "query")
    })
    public Responses.ListResponse list(RequestParams<EducationNhm> requestParams) {
        List<EducationNhm> list = educationNhmService.gets(requestParams);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(method = {RequestMethod.PUT}, produces = APPLICATION_JSON)
    public ApiResponse save(@RequestBody List<EducationNhm> request) {
        educationNhmService.save(request);
        return ok();
    }

    // QueryDsl
    @RequestMapping(value = "/queryDsl", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyNm", value = "회사명", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ceo", value = "대표자", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bizno", value = "사업자번호", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "useYn", value = "사용여부", dataType = "String", paramType = "query")
    })
    public Responses.ListResponse listQueryDsl(RequestParams<EducationNhm> requestParams) {
        List<EducationNhm> list = educationNhmService.getByQueryDsl(requestParams);

        /*Pageable pageable = requestParams.getPageable();
        list.subList(0, 2);  //from, to

        page pages = new pageImpl<>(list, 1, list.size())*/
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(value = "/queryDslOne", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query")
    })
    public EducationNhm selectOne(RequestParams<EducationNhm> requestParams) {
//    public Company selectOne(@RequestParam("id") long id) {
        long id = requestParams.getLong("id", 0);

        EducationNhm educationNhm = educationNhmService.getOneByQueryDsl(id);
        return educationNhm;
    }

    @RequestMapping(value = "/queryDsl", method = {RequestMethod.PUT}, produces = APPLICATION_JSON)
    public ApiResponse saveQueryDsl(@RequestBody List<EducationNhm> request) {
        educationNhmService.saveByQueryDsl(request);
        return ok();
    }

    // MyBatis
/*    @RequestMapping(value = "/myBatis", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyNm", value = "회사명", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ceo", value = "대표자", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bizno", value = "사업자번호", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "useYn", value = "사용여부", dataType = "String", paramType = "query")
    })
    public Responses.ListResponse listMyBatis(RequestParams<EducationNhm> requestParams) {
        List<EducationNhm> list = educationNhmService.getByMyBatis(requestParams);
        return Responses.ListResponse.of(list);
    }

    @RequestMapping(value = "/myBatisOne", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query")
    })
    public EducationNhm selectOneMyBatis(RequestParams<EducationNhm> requestParams) {
//    public Company selectOneMyBatis(@RequestParam("id") long id) {
        long id = requestParams.getLong("id", 0);

        EducationNhm educationNhm = educationNhmService.getOneByMyBatis(id);
        return educationNhm;
    }

    @RequestMapping(value = "/myBatis", method = {RequestMethod.PUT}, produces = APPLICATION_JSON)
    public ApiResponse saveMyBatis(@RequestBody List<EducationNhm> request) {
        educationNhmService.saveByMyBatis(request);
        return ok();
    }*/

    /**** 엑셀 ****/
    @ApiOperation(value = "엑셀다운로드", notes = "/resources/excel/education_nhm.xlsx")
    @RequestMapping(value = "/excelDown", method = {RequestMethod.POST}, produces = APPLICATION_JSON)
    public void excelDown(RequestParams<EducationNhm> requestParams, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<EducationNhm> list = educationNhmService.getByQueryDsl(requestParams);
        ExcelUtils.renderExcel("/excel/education_nhm.xlsx", list, "Education_" + DateUtils.getYyyyMMddHHmmssWithDate(), request, response);
    }
}