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
  private Long user_id;

  @Id
  private Long id;

  @JsonProperty("original-address")
  private String original_address;


  public ToCleanAddress() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOriginal_address() {
    return original_address;
  }

  public void setOriginal_address(String original_address) {
    this.original_address = original_address;
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }
}
