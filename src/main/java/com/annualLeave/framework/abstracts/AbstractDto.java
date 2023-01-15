package com.annualLeave.framework.abstracts;

import com.annualLeave.framework.generic.GenericDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public abstract class AbstractDto implements GenericDto {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private LocalDateTime createDate;
}
