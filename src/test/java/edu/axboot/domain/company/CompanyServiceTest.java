package edu.axboot.domain.company;


import com.chequer.axboot.core.parameter.RequestParams;
import edu.axboot.AXBootApplication;
import lombok.extern.java.Log;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Log
@RunWith(SpringRunner.class)    // 테스트 시 RunWith와 SpringBootTest가 들어가야 함
@SpringBootTest(classes = AXBootApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    public static long testId = 0;  // 전역 변수를 계속 0으로 초기화되지 않고 사용하던 값을 쓰기 위해

    @Test   // 단위 테스트할 수 있는 메서드로 만들겠다
    public void testGetQueryDsl() { // CompanyService.java의 getByQueryDsl 테스트를 진행할 것임
        //given: Test 하기 전 사전 준비, 정의
        RequestParams<Company> requestParams = new RequestParams<Company>();    // 실제 인자 받기 어려우니 세팅하기로 함
        requestParams.put("company", "");
        requestParams.put("ceo", "");
        requestParams.put("bizno", "");

        //when: 실제 Action(호출)하고
        List<Company> result = this.companyService.getByQueryDsl(requestParams);    // 실제 서비스의 메서드를 호출

        //then: 결과가 성공했느냐
        assertTrue(result.size()>0);
        // assertTrue: 안에 들어간 인자가 True인지 확인하는 함수. 결과의 사이즈가 0보다 크면 참이라는 의미다.
    }

    // 이번엔 Company 객체가 아닌 String 3개를 받는 메서드로 테스트
    @Test
    public void testGetQueryDslByString() {
        //given
        String companyNm = "Tesla";
        String ceo = "";
        String bizno = "";

        //when
        List<Company> result = this.companyService.getByQueryDsl(companyNm, ceo, bizno);    // CompanyService에 오버로드함

        //then
        assertTrue(result.size()>0);
    }

    @Test
    public void test1_testSaveQueryDsl_creates() {
        //given
        List<Company> request = new ArrayList<Company>();

        Company company = new Company();
        company.setCompanyNm("Facebook");
        company.setCeo("Mark");
        company.setBizno("444444");
        company.set__created__(true);

        request.add(company);

        //when
        testId = this.companyService.saveByQueryDsl(request);

        //then
        assertTrue(testId > 0);
    }

    @Test
    public void test2_testSaveQueryDsl_updates() {
        //given
        List<Company> request = new ArrayList<Company>();

        Company company = new Company();
        company.setId(testId);
        company.setCompanyNm("FaceBook");
        company.setCeo("Mark Zuckerberg");
        company.setBizno("666666");
        company.set__modified__(true);

        request.add(company);

        //when
        long result = this.companyService.saveByQueryDsl(request);

        //then
        assertTrue(result > 0);
    }

    // 해당 거래처 이름이 기존 DB에 있는 게 맞는지 확인하는 작업이 필요함.
    @Test
    public void test3_SaveQueryDsl_selectOne() {
        //given
        String companyNm = "FaceBook";

        //when
        Company result = this.companyService.getOneByQueryDsl(testId);    // CompanyService에 오버로드함

        //then
        assertTrue(result.getCompanyNm().equals(companyNm));    // 받아온 객체의 이름이 companyNm과 같은지?
    }

    @Test
    public void test4_testSaveQueryDsl_deletes() {
        //given
        List<Company> request = new ArrayList<Company>();

        Company company = new Company();
        company.setId(testId);
        company.set__deleted__(true);

        request.add(company);

        //when
        long result = this.companyService.saveByQueryDsl(request);

        //then
        assertTrue(result > 0);
    }

    // 여러 건이 아닌 단 건을 다루어 보자
    @Test
    public void test10_testSaveQueryDsl_create() {
        //given
        Company company = new Company();
        company.setCompanyNm("Facebook");
        company.setCeo("Mark");
        company.setBizno("444444");
        company.set__created__(true);

        //when
        testId = this.companyService.saveOneByQueryDsl(company);

        //then
        assertTrue(testId > 0);
    }

    @Test
    public void test11_testSaveQueryDsl_update() {
        //given
        Company company = new Company();
        company.setId(testId);
        company.setCompanyNm("FaceBook");
        company.setCeo("Mark Zuckerberg");
        company.setBizno("666666");
        company.set__modified__(true);

        //when
        long result = this.companyService.saveOneByQueryDsl(company);

        //then
        assertTrue(result > 0);
    }

    @Test
    public void test12_testSaveQueryDsl_delete() {
        //given
        Company company = new Company();
        company.setId(testId);
        company.set__deleted__(true);

        //when
        long result = this.companyService.saveOneByQueryDsl(company);

        //then
        assertTrue(result > 0);
    }

}
