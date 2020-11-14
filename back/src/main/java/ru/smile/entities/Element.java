package ru.smile.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Element {

 @Id
 @SequenceGenerator(name = "elementIdSeq", sequenceName = "element_id_seq", allocationSize = 1)
 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "elementIdSeq")
 private Long tableId;

 private String id;
 private String guid;
 private String val;
 private String tname;
 private String stname;
 private String content;
 private Long origin;
 private Long historical;

 public Element() {
 }

 public Long getTableId() {
  return tableId;
 }

 public void setTableId(Long tableId) {
  this.tableId = tableId;
 }

 public String getId() {
  return id;
 }

 public void setId(String id) {
  this.id = id;
 }

 public String getGuid() {
  return guid;
 }

 public void setGuid(String guid) {
  this.guid = guid;
 }

 public String getVal() {
  return val;
 }

 public void setVal(String val) {
  this.val = val;
 }

 public String getTname() {
  return tname;
 }

 public void setTname(String tname) {
  this.tname = tname;
 }

 public String getStname() {
  return stname;
 }

 public void setStname(String stname) {
  this.stname = stname;
 }

 public String getContent() {
  return content;
 }

 public void setContent(String content) {
  this.content = content;
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
}
