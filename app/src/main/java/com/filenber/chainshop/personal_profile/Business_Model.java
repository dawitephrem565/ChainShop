package com.filenber.chainshop.personal_profile;

public class Business_Model {
  String Username;
  String Qoute_Descr;
  String Profile_Img;


  public Business_Model() {
  }

  public Business_Model(String username, String qoute_Descr, String profile_Img) {
    Username = username;
    Qoute_Descr = qoute_Descr;
    Profile_Img = profile_Img;
  }

  public String getUsername() {
    return Username;
  }

  public void setUsername(String username) {
    Username = username;
  }

  public String getQoute_Descr() {
    return Qoute_Descr;
  }

  public void setQoute_Descr(String qoute_Descr) {
    Qoute_Descr = qoute_Descr;
  }

  public String getProfile_Img() {
    return Profile_Img;
  }

  public void setProfile_Img(String profile_Img) {
    Profile_Img = profile_Img;
  }
}
