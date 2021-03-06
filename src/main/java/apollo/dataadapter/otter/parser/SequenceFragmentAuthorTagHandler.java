package apollo.dataadapter.otter.parser;
import apollo.datamodel.*;

public class SequenceFragmentAuthorTagHandler extends TagHandler{

  public void handleEndElement(
    OtterContentHandler theContentHandler,
    String namespaceURI,
    String localName,
    String qualifiedName
  ){
    AssemblyFeature assFrag = (AssemblyFeature)theContentHandler.getStackObject();
    assFrag.addProperty(TagHandler.AUTHOR, getCharacters());

    super.handleEndElement( theContentHandler, namespaceURI, localName, qualifiedName);
  }

  public String getFullName(){
    return "otter:sequence_set:sequence_fragment:author";
  }
  
  public String getLeafName() {
    return "author";
  }
}//end TagHandler
