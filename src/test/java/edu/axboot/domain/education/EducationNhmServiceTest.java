package edu.axboot.domain.education;

import edu.axboot.AXBootApplication;
import lombok.extern.java.Log;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AXBootApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NhmGridServiceTest {

    @Autowired
    private NhmGridService nhmGridService;

    public static long testId = 0;

    @Test
    public void testGetQueryDsl() {
        //given
        String company = "";
        String ceo = "";
        String bizno = "";
        String useYn = "A";

        // when
        List<NhmGrid> result = this.nhmGridService.getByQueryDsl(company, ceo, bizno, useYn);

        // then
        assertTrue(result.size()>0);
    }
}
