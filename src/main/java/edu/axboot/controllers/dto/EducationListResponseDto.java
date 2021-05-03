package edu.axboot.controllers.dto;

import edu.axboot.domain.education.book.EducationBook;
import lombok.Getter;

@Getter
public class EducationListResponseDto {

    // 그리드 화면에 조회되는 항목만 가져오겠다
    private Long id;
    private String companyNm;
    private String ceo;
    private String useYn;

    public EducationListResponseDto(EducationBook entity) {
        this.id = entity.getId();
        this.companyNm = entity.getCompanyNm();
        this.ceo = entity.getCeo();
        this.useYn = entity.getUseYn();
    }

}
