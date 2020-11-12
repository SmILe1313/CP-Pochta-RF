package ru.smile.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** Нормализованная структура после обработки /1.0/clean/address
 * 		"address-type": "DEFAULT",
 * 		"area": "string",
 * 		"building": "string",
 * 		"corpus": "string",
 * 		"hotel": "string",
 * 		"house": "string",
 * 		"id": "string",
 * 		"index": "string",
 * 		"letter": "string",
 * 		"location": "string",
 * 		"num-address-type": "string",
 * 		"original-address": "string",
 * 		"place": "string",
 * 		"quality-code": "GOOD",
 * 		"region": "string",
 * 		"room": "string",
 * 		"slash": "string",
 * 		"street": "string",
 * 		"validation-code": "CONFIRMED_MANUALLY"
 *
 * Код качества должен быть: GOOD, POSTAL_BOX, ON_DEMAND или UNDEF_05.
 * Код проверки должен быть: VALIDATED, OVERRIDDEN или CONFIRMED_MANUALLY.
 *  */

@Entity
@Table(name = "clean_address")
public class CleanAddress {

  @JsonIgnore
  public final static Set<String> goodQuality = new HashSet<>(Arrays.asList("GOOD", "POSTAL_BOX", "ON_DEMAND", "UNDEF_05"));
  @JsonIgnore
  public final static Set<String> goodValidation = new HashSet<>(Arrays.asList("VALIDATED", "OVERRIDDEN", "CONFIRMED_MANUALLY"));

  @JsonIgnore
  private Long userId;

  @Id
  private Long id;

  @JsonProperty("address-type")
  private String addressType;

  private String area;

  private String building;

  private String corpus;

  private String hotel;

  private String house;

  private String index;

  private String letter;

  private String location;

  @JsonProperty("num-address-type")
  private String numAddressType;

  private String place;

  private String region;

  private String room;

  private String slash;

  private String street;

  @JsonProperty("original-address")
  private String originalAddress;

  @JsonProperty("quality-code")
  private String qualityCode;

  @JsonProperty("validation-code")
  private String validationCode;

  public CleanAddress() {
  }

  public CleanAddress(Long id, String addressType, String area, String building, String corpus, String hotel, String house, String index, String letter, String location, String numAddressType, String originalAddress, String place, String qualityCode, String region, String room, String slash, String street, String validationCode) {
    this.id = id;
    this.addressType = addressType;
    this.area = area;
    this.building = building;
    this.corpus = corpus;
    this.hotel = hotel;
    this.house = house;
    this.index = index;
    this.letter = letter;
    this.location = location;
    this.numAddressType = numAddressType;
    this.originalAddress = originalAddress;
    this.place = place;
    this.qualityCode = qualityCode;
    this.region = region;
    this.room = room;
    this.slash = slash;
    this.street = street;
    this.validationCode = validationCode;
  }

  public String getAddressType() {
    return addressType;
  }

  public void setAddressType(String addressType) {
    this.addressType = addressType;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public String getCorpus() {
    return corpus;
  }

  public void setCorpus(String corpus) {
    this.corpus = corpus;
  }

  public String getHotel() {
    return hotel;
  }

  public void setHotel(String hotel) {
    this.hotel = hotel;
  }

  public String getHouse() {
    return house;
  }

  public void setHouse(String house) {
    this.house = house;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public String getLetter() {
    return letter;
  }

  public void setLetter(String letter) {
    this.letter = letter;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getNumAddressType() {
    return numAddressType;
  }

  public void setNumAddressType(String numAddressType) {
    this.numAddressType = numAddressType;
  }

  public String getOriginalAddress() {
    return originalAddress;
  }

  public void setOriginalAddress(String originalAddress) {
    this.originalAddress = originalAddress;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public String getQualityCode() {
    return qualityCode;
  }

  public void setQualityCode(String qualityCode) {
    this.qualityCode = qualityCode;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public String getSlash() {
    return slash;
  }

  public void setSlash(String slash) {
    this.slash = slash;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getValidationCode() {
    return validationCode;
  }

  public void setValidationCode(String validationCode) {
    this.validationCode = validationCode;
  }

  public Boolean isGoodQuality () {
    return goodQuality.contains(this.qualityCode);
  }

  public Boolean isValidationQuality () {
    return goodValidation.contains(this.validationCode);
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

}
