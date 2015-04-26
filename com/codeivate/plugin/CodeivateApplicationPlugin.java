package com.codeivate.plugin;

import com.codeivate.plugin.data.CodeivateSettings;
import com.codeivate.plugin.keyhandlers.CodeivateTypedActionHandler;
import com.codeivate.plugin.logic.CodeivateGame;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.extensions.PluginId;
import org.jetbrains.annotations.NotNull;

























public class CodeivateApplicationPlugin
  implements ApplicationComponent
{
  private static final String IDEACODEIVATE_COMPONENT_NAME = "CodeivateApplicationPlugin";
  private CodeivateTypedActionHandler codeivateHandler;
  private static Logger LOG = Logger.getInstance(CodeivateApplicationPlugin.class);
  

  private static CodeivateSettings codeivateSettings;
  

  public CodeivateApplicationPlugin(Application app)
  {
    Application myApp = app;
  }
  
  @NotNull
  public static CodeivateApplicationPlugin getInstance() {
    CodeivateApplicationPlugin tmp13_10 = ((CodeivateApplicationPlugin)ApplicationManager.getApplication().getComponent("CodeivateApplicationPlugin"));
    

























































    if (tmp13_10 == null) throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[] { "com/codeivate/plugin/CodeivateApplicationPlugin", "getInstance" })); return tmp13_10;
  }
  




  @NotNull
  public String getComponentName()
  {
    String tmp2_0 = "CodeivateApplicationPlugin";
    



































































    if (tmp2_0 == null) throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[] { "com/codeivate/plugin/CodeivateApplicationPlugin", "getComponentName" })); return tmp2_0;
  }
  





  public void initComponent()
  {
    ApplicationManager.getApplication().invokeLater(new Runnable()
    {


      public void run()
      {

        CodeivateSettings codeivateSettings = CodeivateSettings.getInstance();
        
        CodeivateApplicationPlugin codeivateApplicationPlugin = (CodeivateApplicationPlugin)ApplicationManager.getApplication().getComponent("CodeivateApplicationPlugin");
        PluginId pluginId = PluginManager.getPluginByClassName("com.codeivate.plugin.CodeivateApplicationPlugin");
        IdeaPluginDescriptor plugin = PluginManager.getPlugin(pluginId);
        if (plugin != null) {
          codeivateSettings.setCodeivate_version(plugin.getVersion());
        } else {
          codeivateSettings.setCodeivate_version("idea-na");
        }
        CodeivateApplicationPlugin.LOG.info("Initialising Codeivate " + codeivateSettings.getCodeivate_version());
        
        CodeivateGame.getInstance().authenticate();


      }
      


    });
    EditorActionManager manager = EditorActionManager.getInstance();
    TypedAction action = manager.getTypedAction();
    


    this.codeivateHandler = new CodeivateTypedActionHandler(action.getHandler());
    action.setupHandler(this.codeivateHandler);
    
    LOG.debug("done");
  }
  



  public void disposeComponent()
  {
    LOG.debug("disposeComponent");
    
    EditorActionManager manager = EditorActionManager.getInstance();
    TypedAction action = manager.getTypedAction();
    action.setupHandler(this.codeivateHandler.getOriginalTypedHandler());
    LOG.info("Codeivate shutdown");
  }
}
