package apollo.dataadapter.ensj.controller;

import apollo.dataadapter.ensj.model.*;

/**
 * Ask the Controller to make the view do a read.
**/
public class ReadHandler extends EventHandler{
  
  public ReadHandler(Controller controller, String key) {
    super(controller, key);
  }
  
  
  public void doAction(Model model){
    getController().doRead();
  }
}
