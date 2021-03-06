package apollo.dataadapter.otter.parser;
import apollo.datamodel.*;

public class TranscriptCdsEndNotFoundTagHandler extends TagHandler{
  public void handleEndElement(
    OtterContentHandler theContentHandler,
    String namespaceURI,
    String localName,
    String qualifiedName
  ){
    Transcript transcript = (Transcript)theContentHandler.getStackObject();
    transcript.addProperty(CDS_END_NOT_FOUND, getCharacters());
    super.handleEndElement( theContentHandler, namespaceURI, localName, qualifiedName);
  }

  public String getFullName(){
    return "otter:sequence_set:locus:transcript:cds_end_not_found";
  }//end getFullName
  
  public String getLeafName() {
    return "cds_end_not_found";
  }//end getLeafName
}//end TagHandler
