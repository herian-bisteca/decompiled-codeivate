package com.codeivate.plugin;

import com.intellij.ide.util.PropertiesComponent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;







@Deprecated
public class WatchAndSave
  implements Observer
{
  PropertiesComponent instance;
  private ArrayList<String> methods = new ArrayList();
  
  public WatchAndSave()
  {
    this.methods.add("user_id");
    this.methods.add("user_token");
    this.methods.add("machine_id");
    this.methods.add("machine_name");
    this.instance = PropertiesComponent.getInstance();
  }
  



  public void update(Observable o, Object arg)
  {
    try
    {
      if (this.methods.contains(arg.toString())) {
        this.instance.setValue(arg.toString(), o.getClass().getMethod(arg.toString(), new Class[0]).toString());
      }
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }
}
