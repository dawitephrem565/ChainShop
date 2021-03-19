package com.filenber.chainshop;

public class User_Id {
  String Id;
  public <T extends User_Id> T withId(final String id)
  {
    this.Id=id;
    return (T)this;
  }
}
