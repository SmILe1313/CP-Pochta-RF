package ru.smile.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Fio {

  @Id
  @SequenceGenerator(name = "fioIdSeq", sequenceName = "fio_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fioIdSeq")
  private Long id;

  private String original;
  private String fullname;
  private String surname;
  private String name;
  private String middlename;
  private Long pcategory;
  private String unparsed;

  public Fio() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOriginal() {
    return original;
  }

  public void setOriginal(String original) {
    this.original = original;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMiddlename() {
    return middlename;
  }

  public void setMiddlename(String middlename) {
    this.middlename = middlename;
  }

  public Long getPcategory() {
    return pcategory;
  }

  public void setPcategory(Long pcategory) {
    this.pcategory = pcategory;
  }

  public String getUnparsed() {
    return unparsed;
  }

  public void setUnparsed(String unparsed) {
    this.unparsed = unparsed;
  }
}
