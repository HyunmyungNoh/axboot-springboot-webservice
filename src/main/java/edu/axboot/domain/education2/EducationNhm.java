package edu.axboot.domain.education2;


import com.chequer.axboot.core.annotations.ColumnPosition;
import edu.axboot.domain.SimpleJpaModel;
import edu.axboot.utils.SessionUtils;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Clock;
import java.time.Instant;


@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "EDUCATION_NHM")
//@IdClass(NhmGrid.NhmGridId.class)
//@Alias("nhmGrid")
public class EducationNhm extends SimpleJpaModel<Long> {

	@Id
	@Column(name = "ID", precision = 19, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "COMPANY_NM", length = 30)
	private String companyNm;

	@Column(name = "CEO", length = 30)
	private String ceo;

	@Column(name = "BIZNO", length = 10)
	private String bizno;

	@Column(name = "TEL", length = 18)
	private String tel;

	@Column(name = "ZIP", length = 7)
	private String zip;

	@Column(name = "ADDRESS", length = 200)
	private String address;

	@Column(name = "ADDRESS_DETAIL", length = 200)
	private String addressDetail;

	@Column(name = "EMAIL", length = 50)
	private String email;

	@Column(name = "REMARK", length = 500)
	private String remark;

	@Column(name = "USE_YN", length = 1)
	private String useYn;

	@Column(name = "CREATED_AT", updatable = false)
	@ColumnPosition(Integer.MAX_VALUE - 4)
	protected Instant createdAt;

	@Column(name = "CREATED_BY", updatable = false)
	@ColumnPosition(Integer.MAX_VALUE - 3)
	protected String createdBy;

	@Column(name = "UPDATED_AT")
	@ColumnPosition(Integer.MAX_VALUE - 2)
	protected Instant updatedAt;

	@Column(name = "UPDATED_BY")
	@ColumnPosition(Integer.MAX_VALUE - 1)
	protected String updatedBy;

	@PrePersist
	protected void onPersist() {
		this.createdBy = this.updatedBy = SessionUtils.getCurrentLoginUserCd();
		this.createdAt = this.updatedAt = Instant.now(Clock.systemUTC());
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedBy = SessionUtils.getCurrentLoginUserCd();
		this.updatedAt = Instant.now(Clock.systemUTC());
	}

	@Override
	public Long getId() {
	return id;
	}
}