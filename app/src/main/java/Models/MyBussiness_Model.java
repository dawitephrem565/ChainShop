package Models;

import document_id_package.Bussiness_Doc_Id;

public class MyBussiness_Model extends Bussiness_Doc_Id {
  String Business_Name ;
  String Business_Descr;
  String Business_Img;
  String Business_Commission;

  public MyBussiness_Model() {
  }

  public MyBussiness_Model(String business_Name, String business_Descr, String business_Img, String business_Commission) {
    Business_Name = business_Name;
    Business_Descr = business_Descr;
    Business_Img = business_Img;
    Business_Commission = business_Commission;
  }

  public String getBusiness_Name() {
    return Business_Name;
  }

  public void setBusiness_Name(String business_Name) {
    Business_Name = business_Name;
  }

  public String getBusiness_Descr() {
    return Business_Descr;
  }

  public void setBusiness_Descr(String business_Descr) {
    Business_Descr = business_Descr;
  }

  public String getBusiness_Img() {
    return Business_Img;
  }

  public void setBusiness_Img(String business_Img) {
    Business_Img = business_Img;
  }

  public String getBusiness_Commission() {
    return Business_Commission;
  }

  public void setBusiness_Commission(String business_Commission) {
    Business_Commission = business_Commission;
  }
}
