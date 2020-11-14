package ru.smile.entities;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.List;

@Entity
public class AddrResponse {

  @Id
  @SequenceGenerator(name = "addrResponseIdSeq", sequenceName = "addr_response_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addrResponseIdSeq")
  private Long tableId;

  private String id;

  private String guid;
  private String inaddr;
  private String outaddr;
  private Long addrType;
  private Long direct;
  private String accuracy;
  private Integer delivery;
  private String longitude;
  private String latitude;
  private Long index;
  private Integer deliveryArea;
  private String postoffice;
  private Boolean placeChanged;
  private String missing;
  private String unparsed;
  private Long origin;
  private Long historical;

//  @ElementCollection
  @OneToMany(fetch = FetchType.EAGER)
  private List<Element> element;

  public AddrResponse() {
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

  public String getInaddr() {
    return inaddr;
  }

  public void setInaddr(String inaddr) {
    this.inaddr = inaddr;
  }

  public String getOutaddr() {
    return outaddr;
  }

  public void setOutaddr(String outaddr) {
    this.outaddr = outaddr;
  }

  public Long getAddrType() {
    return addrType;
  }

  public void setAddrType(Long addrType) {
    this.addrType = addrType;
  }

  public Long getDirect() {
    return direct;
  }

  public void setDirect(Long direct) {
    this.direct = direct;
  }

  public String getAccuracy() {
    return accuracy;
  }

  public void setAccuracy(String accuracy) {
    this.accuracy = accuracy;
  }

  public Integer getDelivery() {
    return delivery;
  }

  public void setDelivery(Integer delivery) {
    this.delivery = delivery;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public Long getIndex() {
    return index;
  }

  public void setIndex(Long index) {
    this.index = index;
  }

  public Integer getDeliveryArea() {
    return deliveryArea;
  }

  public void setDeliveryArea(Integer deliveryArea) {
    this.deliveryArea = deliveryArea;
  }

  public String getPostoffice() {
    return postoffice;
  }

  public void setPostoffice(String postoffice) {
    this.postoffice = postoffice;
  }

  public Boolean getPlaceChanged() {
    return placeChanged;
  }

  public void setPlaceChanged(Boolean placeChanged) {
    this.placeChanged = placeChanged;
  }

  public String getMissing() {
    return missing;
  }

  public void setMissing(String missing) {
    this.missing = missing;
  }

  public String getUnparsed() {
    return unparsed;
  }

  public void setUnparsed(String unparsed) {
    this.unparsed = unparsed;
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

  public List<Element> getElement() {
    return element;
  }

  public void setElement(List<Element> element) {
    this.element = element;
  }
}
