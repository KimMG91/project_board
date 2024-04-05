package com.project.project_board.Dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

  @NotBlank // *Spring-Boot-starter-validation 필수값
  private String email;
  
  @NotBlank
  private String password;
}
