package Models;

public class MyBusiness_Marketer_Model {
  String User_Name;
  String User_Profile_Img;
  String User_Id;

  public MyBusiness_Marketer_Model() {
  }

  public MyBusiness_Marketer_Model(String user_Name, String user_Profile_Img, String user_Id) {
    User_Name = user_Name;
    User_Profile_Img = user_Profile_Img;
    User_Id = user_Id;
  }

  public String getUser_Name() {
    return User_Name;
  }

  public void setUser_Name(String user_Name) {
    User_Name = user_Name;
  }

  public String getUser_Profile_Img() {
    return User_Profile_Img;
  }

  public void setUser_Profile_Img(String user_Profile_Img) {
    User_Profile_Img = user_Profile_Img;
  }

  public String getUser_Id() {
    return User_Id;
  }

  public void setUser_Id(String user_Id) {
    User_Id = user_Id;
  }
}
