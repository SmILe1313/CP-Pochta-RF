package ru.smile.entities;

public class ValidateResponseCounts {

  private Long good = 0L;
  private Long middle = 0L;
  private Long bad = 0L;
  private Long total = 0L;

  public ValidateResponseCounts() {
  }

  public ValidateResponseCounts(Long good, Long middle, Long bad) {
    this.good = good;
    this.middle = middle;
    this.bad = bad;
    this.total = getTotal();
  }

  public Long getGood() {
    return good;
  }

  public void setGood(Long good) {
    this.good = good;
  }

  public Long getMiddle() {
    return middle;
  }

  public void setMiddle(Long middle) {
    this.middle = middle;
  }

  public Long getBad() {
    return bad;
  }

  public void setBad(Long bad) {
    this.bad = bad;
  }

  public Long getTotal() {
    return good + middle + bad;
  }

  public void setTotal(Long total) {
    this.total = total;
  }
}
