package com.ttogal.domain.user.entity.enums;

import lombok.Getter;

@Getter
public enum UserJobStatus {
    STUDENT("학생"),
    EMPLOYEE("직장인"),
    FREELANCER("프리랜서"),
    JOB_SEEKING("구직 중"),
    ENTREPRENEUR("사업가"),
    ON_LEAVE("휴직 중"),
    OTHER("기타");

    private final String description;

    UserJobStatus(String description) {
        this.description = description;
    }
}
