package ru.smile.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class AddrRequest {

  @Id
  @SequenceGenerator(name = "addrRequestIdSeq", sequenceName = "addr_request_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addrRequestIdSeq")
  private Long id;

  private String val;
  private String content;
  private String stname;

  public AddrRequest() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AddrRequest(String val) {
    this.val = val;
  }

  public String getVal() {
    return val;
  }

  public void setVal(String val) {
    this.val = val;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getStname() {
    return stname;
  }

  public void setStname(String stname) {
    this.stname = stname;
  }
}
