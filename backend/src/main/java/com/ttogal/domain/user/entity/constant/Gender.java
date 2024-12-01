package com.ttogal.domain.user.entity.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Gender {
  MALE("남자"),
  FEMALE("여자");
  private final String description;
}
