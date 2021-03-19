package document_id_package;

public class User_Id {
  public String Id;
  public <T extends User_Id> T withId(final String id)
  {
    this.Id=id;
    return (T)this;
  }
}
