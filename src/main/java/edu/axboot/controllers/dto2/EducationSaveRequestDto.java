package edu.axboot.controllers.dto2;

import edu.axboot.domain.education2.book.EducationBook2;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EducationSaveRequestDto {
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

    @Builder
    public EducationSaveRequestDto(String companyNm, String ceo, String bizno, String tel, String zip, String address,
                                   String addressDetail, String email,  String remark, String useYn) {
        this.companyNm = companyNm;
        this.ceo = ceo;
        this.bizno = bizno;
        this.companyNm = companyNm;
        this.tel = tel;
        this.zip = zip;
        this.address = address;
        this.addressDetail = addressDetail;
        this.email = email;
        this.remark = remark;
        this.useYn = useYn;
    }

    public EducationBook2 toEntity() {
        return EducationBook2.builder()
                .companyNm(companyNm)
                .ceo(ceo)
                .bizno(bizno)
                .tel(tel)
                .zip(zip)
                .address(address)
                .addressDetail(addressDetail)
                .email(email)
                .remark(remark)
                .useYn(useYn)
                .build();
    }
}
