package com.filenber.chainshop.personal_profile;

import document_id_package.Pinned_doc_id;

public class Pinned_Model extends Pinned_doc_id {

  String Product_name;
  String Product_descr;
  String Product_Image;
  String Product_price;
  String Owner_id;


  public Pinned_Model() {
  }

  public Pinned_Model(String product_name, String product_descr, String product_Image, String product_price, String owner_id) {
    Product_name = product_name;
    Product_descr = product_descr;
    Product_Image = product_Image;
    Product_price = product_price;
    Owner_id = owner_id;
  }

  public String getProduct_name() {
    return Product_name;
  }

  public void setProduct_name(String product_name) {
    Product_name = product_name;
  }

  public String getProduct_descr() {
    return Product_descr;
  }

  public void setProduct_descr(String product_descr) {
    Product_descr = product_descr;
  }

  public String getProduct_Image() {
    return Product_Image;
  }

  public void setProduct_Image(String product_Image) {
    Product_Image = product_Image;
  }

  public String getProduct_price() {
    return Product_price;
  }

  public void setProduct_price(String product_price) {
    Product_price = product_price;
  }

  public String getOwner_id() {
    return Owner_id;
  }

  public void setOwner_id(String owner_id) {
    Owner_id = owner_id;
  }
}
