package document_id_package;

public class Pinned_doc_id {
  public String Id;
  public <T extends Pinned_doc_id> T withId(final String id)
  {
    this.Id=id;
    return (T)this;
  }
}
