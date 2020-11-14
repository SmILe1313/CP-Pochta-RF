package ru.smile.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

/** Запрос
 *  -------------
 *  {
 *  "version": "string",
 *  "reqId": "string",
 *  "fio": "string",
 *  "index": number,
 *  "woFlat": boolean,
 *  "useStructure": boolean,
 *  "useRegionPostOffice": boolean,
 *  "compliance": boolean,
 *  "outLang": string,
 *  "origin": number,
 *  "historical": number,
 *  "addr": [
 *  {
 *  "val": "string",
 *  "content": "string",
 *  "stname": "string"
 *  }
 *  ]
 *  */

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "validate_simple_request")
public class ValidateSimpleRequest {

  @JsonIgnore
  private Long userId;

  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

 @ManyToOne(fetch = FetchType.EAGER)
  private AddrRequest addr;

  public ValidateSimpleRequest() {
  }

  public ValidateSimpleRequest(Long id, AddrRequest addr) {
    this.id = id;
    this.addr = addr;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AddrRequest getAddr() {
    return addr;
  }

  public void setAddr(AddrRequest addr) {
    this.addr = addr;
  }
}
