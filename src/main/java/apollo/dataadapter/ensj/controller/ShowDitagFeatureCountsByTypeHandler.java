package apollo.dataadapter.ensj.controller;

import apollo.dataadapter.ensj.*;
import apollo.dataadapter.ensj.model.*;

import java.sql.*;
import java.util.*;

/**
 * Check to see if our dna-protein align counts have already been initialised. If not,
 * run sql to get them. Populate the table of protein-align types, counts (by type)
 * and set the 'dna-protein align counts by type initialised' flag to true. Tell the gui
 * to display that types list.
**/
public class ShowDitagFeatureCountsByTypeHandler extends EventHandler{
  
  public ShowDitagFeatureCountsByTypeHandler(Controller controller, String key) {
    super(controller, key);
  }
  
  public void doAction(Model model){
    TypesModel myModel = model.getTypesModel();
    HashMap typeCounts;
    log("Fetching & showing ditag align counts by type");
    
    doUpdate();
    
    if(myModel.getTypePanelToShow().equals(myModel.DITAGFEATURE)){
      myModel.setTypePanelToShow(myModel.NONE);
    }else{
      log("Setting type panel to show type "+myModel.DITAGFEATURE);
      myModel.setTypePanelToShow(myModel.DITAGFEATURE);
    }
    
    if(!myModel.isDitagFeatureTypeCountInitialised()){
      typeCounts = getCountsByType(model);
      myModel.setDitagFeatureTypeCounts(typeCounts);
      myModel.setDitagFeatureTypes(new ArrayList(typeCounts.keySet()));
      myModel.setDitagFeatureTypeCountInitialised(true);
    }
    
    doRead();
  }
  
  public HashMap getCountsByType(Model model){
    HashMap returnMap = new HashMap();
    Connection connection = getConnectionForModel(model);


    String sql =
     "select logic_name, count(*) from ditag_feature, analysis where ditag_feature.analysis_id = analysis.analysis_id "+
     "group by ditag_feature.analysis_id";

    ResultSet results;

    try{

      int schema = EnsJConnectionUtil.getEnsemblSchema(connection);

      if (schema > 39) {
        results = connection.createStatement().executeQuery(sql);
        while(results.next()){
          returnMap.put(results.getString(1), new Integer(results.getInt(2)));
        }
      }

      log("Found "+returnMap.keySet().size()+" Different types");
    }catch(SQLException exception){
      throw new NonFatalException(exception.getMessage(), exception);
    }

    return returnMap;
  }
}
