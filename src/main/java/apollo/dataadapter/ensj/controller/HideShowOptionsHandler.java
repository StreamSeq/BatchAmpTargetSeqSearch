package apollo.dataadapter.ensj.controller;

import apollo.dataadapter.ensj.model.*;

/**
 * Declare the options panel (as represented by the model) to be either
 * showing or not, and refresh!
**/
public class HideShowOptionsHandler extends EventHandler{
  
  public HideShowOptionsHandler(Controller controller, String key) {
    super(controller, key);
  }
  
  
  public void doAction(Model model){
    doUpdate();
    
    if(model.isOptionsPanelVisible()){
      model.setOptionsPanelVisible(false);
    }else{
      model.setOptionsPanelVisible(true);
    }
    
    doRead();
  }
}
