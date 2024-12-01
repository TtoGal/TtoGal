package com.ttogal.domain.user.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobStatus {
  STUDENT("학생"),
  EMPLOYEE("직장인"),
  FREELANCER("프리랜서"),
  JOB_SEEKING("구직 중"),
  ENTREPRENEUR("사업가"),
  ON_LEAVE("휴직 중"),
  OTHERS("기타");

  private final String description;

}
