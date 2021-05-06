package edu.axboot.controllers.dto2;

import edu.axboot.domain.education2.book.EducationBook2;
import lombok.Getter;

@Getter
public class EducationResponseDto {
    private Long id;
    private String companyNm;
    private String ceo;
    private String bizno;
    private String tel;
    private String zip;
    private String address;
    private String addressDetail;
    private String email;
    private String remark;
    private String useYn;

    // entity를 Dto로 변환해줌(builder 방식이 아님)
    public EducationResponseDto(EducationBook2 entity) {
        this.id = entity.getId();
        this.companyNm = entity.getCompanyNm();
        this.ceo = entity.getCeo();
        this.bizno = entity.getBizno();
        this.tel = entity.getTel();
        this.zip = entity.getZip();
        this.address = entity.getAddress();
        this.addressDetail = entity.getAddressDetail();
        this.email = entity.getEmail();
        this.remark = entity.getRemark();
        this.useYn = entity.getUseYn();
    }
}
