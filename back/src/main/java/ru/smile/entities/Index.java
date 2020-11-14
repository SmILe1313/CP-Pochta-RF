package ru.smile.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Index {

  @Id
  @SequenceGenerator(name = "indexIdSeq", sequenceName = "index_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "indexIdSeq")
  private Long id;

  private String inindex;
  private String postoffice;
  private String disablecode;

  public Index() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getInindex() {
    return inindex;
  }

  public void setInindex(String inindex) {
    this.inindex = inindex;
  }

  public String getPostoffice() {
    return postoffice;
  }

  public void setPostoffice(String postoffice) {
    this.postoffice = postoffice;
  }

  public String getDisablecode() {
    return disablecode;
  }

  public void setDisablecode(String disablecode) {
    this.disablecode = disablecode;
  }
}
