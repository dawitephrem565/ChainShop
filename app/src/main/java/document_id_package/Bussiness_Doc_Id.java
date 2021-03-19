package document_id_package;

public class Bussiness_Doc_Id {
  public String Id;
  public <T extends Bussiness_Doc_Id> T withId(final String id)
  {
    this.Id=id;
    return (T)this;
  }
}
