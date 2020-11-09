package ru.smile.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

/** Первоначальная структура запроса для обработки /1.0/clean/address
 * "id": "string",
 *  "original-address": "string"
 *  */

@Entity
@Table(name = "to_clean_address")
public class ToCleanAddress {

  @JsonIgnore
  private Long userId;

  @Id
  private Long id;

  @JsonProperty("original-address")
  private String originalAddress;


  public ToCleanAddress() {
  }

  public ToCleanAddress(Long id, String originalAddress) {
    this.id = id;
    this.originalAddress = originalAddress;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOriginalAddress() {
    return originalAddress;
  }

  public void setOriginalAddress(String originalAddress) {
    this.originalAddress = originalAddress;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
