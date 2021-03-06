package apollo.dataadapter.otter.parser;
import apollo.datamodel.*;

public class SequenceFragmentKeywordTagHandler extends TagHandler{
  public void handleEndElement(
    OtterContentHandler theContentHandler,
    String namespaceURI,
    String localName,
    String qualifiedName
  ){
    AssemblyFeature assFeat = (AssemblyFeature)theContentHandler.getStackObject();
    assFeat.addKeyword(getCharacters());
    super.handleEndElement( theContentHandler, namespaceURI, localName, qualifiedName);
  }

  public String getFullName(){
    return "otter:sequence_set:sequence_fragment:keyword";
  }//end getFullName
  
  public String getLeafName() {
    return "keyword";
  }//end getLeafName
}//end TagHandler
