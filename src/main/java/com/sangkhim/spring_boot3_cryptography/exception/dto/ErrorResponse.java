package com.sangkhim.spring_boot3_cryptography.exception.dto;

import java.sql.Timestamp;
import lombok.*;

/**
 *
 *
 * <h2>ErrorResponse</h2>
 *
 * @author aek
 *     <p>Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ErrorResponse {

  /** error code */
  private String code;
  /** short error message */
  private String message;

  /** error cause timestamp */
  private Timestamp timestamp;
}
