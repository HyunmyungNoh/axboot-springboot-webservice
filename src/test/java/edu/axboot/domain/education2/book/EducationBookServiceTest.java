package edu.axboot.domain.education2.book;


import edu.axboot.AXBootApplication;
import edu.axboot.controllers.dto2.EducationResponseDto;
import edu.axboot.controllers.dto2.EducationSaveRequestDto;
import edu.axboot.controllers.dto2.EducationUpdateRequestDto;
import lombok.extern.java.Log;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AXBootApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EducationBookServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(EducationBookServiceTest.class);

    @Autowired
    private EducationBookService2 educationBookService;

    public static long testId = 0;

    @Test
    public void test1_거래처_한건_저장하기() {
        //given
        EducationSaveRequestDto saveDto = new EducationSaveRequestDto().builder()
                .companyNm("스프링테스트")
                .ceo("개발자")
                .bizno("68756")
                .useYn("N")
                .build();

        //when
        testId = this.educationBookService.save(saveDto);
//        logger.info("테스트 ID: " + testId);

        //then
        assertTrue(testId > 0);
    }

    @Test
    public void test2_거래처_한건_불러오기() {
        //given
        long id = testId;

        //when
        EducationResponseDto result = this.educationBookService.findById(id);
        logger.info("테스트 ID: " + id + " / 받아온 ID: " + result.getId());

        //then
        assertTrue(result.getId() == id);
    }

    @Test
    public void test3_거래처_한건_수정_확인하기() {
        //given
        EducationUpdateRequestDto entity = new EducationUpdateRequestDto().builder()
                .tel("01077778888")
                .email("Iwanna@go.home")
                .build();

        //when
        Long result = this.educationBookService.update(testId, entity);
        EducationResponseDto resDto = this.educationBookService.findById(testId);

        //then
        assertTrue(resDto.getTel().equals(entity.getTel())
            && resDto.getEmail().equals(entity.getEmail()));
    }
}
