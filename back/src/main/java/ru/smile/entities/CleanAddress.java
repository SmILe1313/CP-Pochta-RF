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

  public final static Set<String> goodQuality = new HashSet<>(Arrays.asList("GOOD", "POSTAL_BOX", "ON_DEMAND", "UNDEF_05"));
  public final static Set<String> goodValidation = new HashSet<>(Arrays.asList("VALIDATED", "OVERRIDDEN", "CONFIRMED_MANUALLY"));

  @JsonIgnore
  private Long user_id;

  @Id
  private Long id;

  @JsonProperty("address-type")
  private String address_type;

  private String area;

  private String building;

  private String corpus;

  private String hotel;

  private String house;

  private String index;

  private String letter;

  private String location;

  @JsonProperty("num-address-type")
  private String num_address_type;

  @JsonProperty("original-address")
  private String original_address;

  private String place;

  @JsonProperty("quality-code")
  private String quality_code;

  private String region;

  private String room;

  private String slash;

  private String street;

  @JsonProperty("validation-code")
  private String validation_code;

  public CleanAddress() {
  }

  public String getAddress_type() {
    return address_type;
  }

  public void setAddress_type(String address_type) {
    this.address_type = address_type;
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

  public String getNum_address_type() {
    return num_address_type;
  }

  public void setNum_address_type(String num_address_type) {
    this.num_address_type = num_address_type;
  }

  public String getOriginal_address() {
    return original_address;
  }

  public void setOriginal_address(String original_address) {
    this.original_address = original_address;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public String getQuality_code() {
    return quality_code;
  }

  public void setQuality_code(String quality_code) {
    this.quality_code = quality_code;
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

  public String getValidation_code() {
    return validation_code;
  }

  public void setValidation_code(String validation_code) {
    this.validation_code = validation_code;
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public Boolean isGoodQuality () {
    return goodQuality.contains(this.quality_code);
  }

  public Boolean isValidationQuality () {
    return goodValidation.contains(this.validation_code);
  }

}
