package com.filenber.chainshop.personal_profile;

import document_id_package.User_Id;
import document_id_package.followers_id;

public class add_followers_model extends followers_id {
  String Username;
  String tag_username;

  public add_followers_model() {
  }

  public add_followers_model(String username, String tag_username) {
    Username = username;
    this.tag_username = tag_username;
  }

  public String getUsername() {
    return Username;
  }

  public void setUsername(String username) {
    Username = username;
  }

  public String getTag_username() {
    return tag_username;
  }

  public void setTag_username(String tag_username) {
    this.tag_username = tag_username;
  }
}
