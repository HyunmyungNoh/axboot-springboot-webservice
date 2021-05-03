package edu.axboot.domain.education.book;

import com.querydsl.core.BooleanBuilder;
import edu.axboot.controllers.dto.EducationListResponseDto;
import edu.axboot.controllers.dto.EducationResponseDto;
import edu.axboot.controllers.dto.EducationSaveRequestDto;
import edu.axboot.controllers.dto.EducationUpdateRequestDto;
import edu.axboot.domain.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class EducationBookService extends BaseService<EducationBook, Long> {
    private final EducationBookRepository educationBookRepository;

    @Transactional
    public Long save(EducationSaveRequestDto requestDto) {
        return educationBookRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, EducationUpdateRequestDto requestDto) {
        // 좀 더 높은 버전에서는 findById를 사용할 수 있다.
        EducationBook educationBook = educationBookRepository.findOne(id);
        if (educationBook == null) throw new IllegalArgumentException("해당 거래처가 없습니다. id = " + id);

        // 쿼리 없이 이 educationBook 엔티티가 update 작업을 처리하게 된다.
        educationBook.update(requestDto.getTel(), requestDto.getEmail());

        return id;
    }

    public EducationResponseDto findById(Long id) {
        EducationBook entity = educationBookRepository.findOne(id);
        if (entity == null) throw new IllegalArgumentException("해당 거래처가 없습니다. id = " + id);

        return new EducationResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<EducationListResponseDto> getList(String companyNm, String ceo, String bizno) {
        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(companyNm)) builder.and(qEducationBook.companyNm.contains(companyNm));
        if (isNotEmpty(ceo)) builder.and(qEducationBook.ceo.contains(ceo));
        if (isNotEmpty(bizno)) builder.and(qEducationBook.bizno.eq(bizno));

        List<EducationBook> list = select()
                .from(qEducationBook)
                .where(builder)
                .orderBy(qEducationBook.companyNm.asc())
                .fetch();

        return list.stream()
                .map(EducationListResponseDto::new)
                .collect(Collectors.toList());
    }
}
