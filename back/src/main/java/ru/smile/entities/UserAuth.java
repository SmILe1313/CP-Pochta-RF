package ru.smile.entities;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope("prototype")
public class UserAuth {

  private String firstName;

  private UUID responseUuid;

  public UUID getResponseUuid() {
    return responseUuid;
  }

  public void setResponseUuid(UUID responseUuid) {
    this.responseUuid = responseUuid;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
}
