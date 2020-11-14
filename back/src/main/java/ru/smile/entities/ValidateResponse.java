package ru.smile.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.UUID;

/** Ответ
 *
 *  {
 *  "version": "string",
 *  "state": "string",
 *  "fio": {
 *  "original": "string",
 *  "fullname": "string",
 *  "surname": "string",
 *  "name": "string",
 *  "middlename": "string",
 *  "pcategory": number,
 *  "unparsed": "string"
 *  },
 *  "index": {
 *  "inindex": "string",
 *  "postoffice": "string",
 *  "disablecode": "string"
 * }
 *  "addr": {
 *  "id": "string",
 *  "guid": "string",
 *  "inaddr":"string",
 *  "outaddr":"string",
 *  "addrType": number,
 *  "direct": number,
 *  "accuracy":"string",
 *  "delivery": integer,
 *  "longitude": "string",
 *  "latitude": "string",
 *  "accuracy": "string",
 *  "index": number,
 *  "deliveryArea": integer,
 *  "postoffice": "string",
 *  "placeChanged": boolean,
 *  "missing": "string",
 *  "unparsed": "string",
 *  "origin": number,
 *  "historical":number,
 *  "element": [
 *  {
 *  "id": "string",
 *  "guid": "string",
 *  "val": "string",
 *  "tname": "string",
 *  "stname": "string",
 *  "content": "string",
 *  "origin": number,
 *  "historical":number
 *  }
 *  ]
 *  }
 * }
 *  */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "validate_response")
public class ValidateResponse {

  @JsonIgnore
  private Long userId;

  @JsonIgnore
  private UUID responseUuid;

  @Id
  @SequenceGenerator(name = "validateResponseIdSeq", sequenceName = "validate_response_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "validateResponseIdSeq")
  private Long id;

  private String version;
  private String state;

  @ManyToOne(fetch = FetchType.EAGER)
  private Fio fio;

  @ManyToOne(fetch = FetchType.EAGER)
  private Index index;

  @ManyToOne(fetch = FetchType.EAGER)
  private AddrResponse addr;

  public ValidateResponse() {
  }

  public ValidateResponse(String version, String state, Fio fio, Index index, AddrResponse addr) {
    this.version = version;
    this.state = state;
    this.fio = fio;
    this.index = index;
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

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Fio getFio() {
    return fio;
  }

  public void setFio(Fio fio) {
    this.fio = fio;
  }

  public Index getIndex() {
    return index;
  }

  public void setIndex(Index index) {
    this.index = index;
  }

  public AddrResponse getAddr() {
    return addr;
  }

  public void setAddr(AddrResponse addr) {
    this.addr = addr;
  }

  public UUID getResponseUuid() {
    return responseUuid;
  }

  public void setResponseUuid(UUID responseUuid) {
    this.responseUuid = responseUuid;
  }
}
