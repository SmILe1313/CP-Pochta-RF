package ru.smile.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
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
//@Table(name = "validate_request")
public class ValidateRequest {

  @JsonIgnore
  private Long userId;

  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String version;
  private String reqId;
  private String fio;
  private Long index;
  private Boolean woFlat;
  private Boolean useStructure;
  private Boolean useRegionPostOffice;
  private Boolean compliance;
  private String outLang;
  private Long origin;
  private Long historical;

  @OneToMany
//  @ElementCollection
  private List<AddrRequest> addr;// = new ArrayList<AddrRequest>();

  public ValidateRequest() {
  }

  public ValidateRequest(String version, String reqId, String fio, Long index, Boolean woFlat, Boolean useStructure, Boolean useRegionPostOffice, Boolean compliance, String outLang, Long origin, Long historical, List<AddrRequest> addr) {
    this.version = version;
    this.reqId = reqId;
    this.fio = fio;
    this.index = index;
    this.woFlat = woFlat;
    this.useStructure = useStructure;
    this.useRegionPostOffice = useRegionPostOffice;
    this.compliance = compliance;
    this.outLang = outLang;
    this.origin = origin;
    this.historical = historical;
    this.addr = addr;
  }

  public ValidateRequest(Long id, List<AddrRequest> addr) {
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

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getReqId() {
    return reqId;
  }

  public void setReqId(String reqId) {
    this.reqId = reqId;
  }

  public String getFio() {
    return fio;
  }

  public void setFio(String fio) {
    this.fio = fio;
  }

  public Long getIndex() {
    return index;
  }

  public void setIndex(Long index) {
    this.index = index;
  }

  public Boolean getWoFlat() {
    return woFlat;
  }

  public void setWoFlat(Boolean woFlat) {
    this.woFlat = woFlat;
  }

  public Boolean getUseStructure() {
    return useStructure;
  }

  public void setUseStructure(Boolean useStructure) {
    this.useStructure = useStructure;
  }

  public Boolean getUseRegionPostOffice() {
    return useRegionPostOffice;
  }

  public void setUseRegionPostOffice(Boolean useRegionPostOffice) {
    this.useRegionPostOffice = useRegionPostOffice;
  }

  public Boolean getCompliance() {
    return compliance;
  }

  public void setCompliance(Boolean compliance) {
    this.compliance = compliance;
  }

  public String getOutLang() {
    return outLang;
  }

  public void setOutLang(String outLang) {
    this.outLang = outLang;
  }

  public Long getOrigin() {
    return origin;
  }

  public void setOrigin(Long origin) {
    this.origin = origin;
  }

  public Long getHistorical() {
    return historical;
  }

  public void setHistorical(Long historical) {
    this.historical = historical;
  }

  public List<AddrRequest> getAddr() {
    return addr;
  }

  public void setAddr(List<AddrRequest> addr) {
    this.addr = addr;
  }
}
