package com.ttogal.domain.user.entity;

import com.ttogal.domain.user.entity.constant.Gender;
import com.ttogal.domain.user.entity.constant.JobStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name="user_id")
  private Long userId;

  @Column(nullable = false,length=50)
  private String name;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = false,unique = true, length=30)
  private String nickname;

  @Column(nullable=false,unique=true,length=100)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false, name="job_status")
  private JobStatus jobStatus;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false)
  private Gender gender;

  @Builder
  public User(String name,String email, String nickname, String password, JobStatus jobStatus, LocalDate birthDate, Gender gender) {
    this.name = name;
    this.email = email;
    this.nickname = nickname;
    this.password = password;
    this.jobStatus = jobStatus;
    this.birthDate = birthDate;
    this.gender = gender;
  }
}
