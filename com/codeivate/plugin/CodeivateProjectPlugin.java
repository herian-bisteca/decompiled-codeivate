package com.codeivate.plugin;

import com.codeivate.plugin.ui.statusbar.CodeivateWidget;
import com.codeivate.plugin.utils.StatusBarUtils;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class CodeivateProjectPlugin
  implements ProjectComponent
{
  protected final Project myProject;
  private CodeivateWidget myBranchWidget;
  
  public CodeivateProjectPlugin(Project project)
  {
    this.myProject = project;
  }
  

  public void initComponent() {}
  

  public void disposeComponent() {}
  

  @NotNull
  public String getComponentName()
  {
    String tmp2_0 = "CodeivateProjectPlugin";
    






























    if (tmp2_0 == null) throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[] { "com/codeivate/plugin/CodeivateProjectPlugin", "getComponentName" })); return tmp2_0;
  }
  
  public void projectOpened() {
    if (!ApplicationManager.getApplication().isHeadlessEnvironment()) {
      this.myBranchWidget = new CodeivateWidget(this.myProject);
      StatusBarUtils.installStatusBarWidget(this.myProject, this.myBranchWidget);
    }
  }
  
  public void projectClosed() {
    if (this.myBranchWidget != null) {
      StatusBarUtils.removeStatusBarWidget(this.myProject, this.myBranchWidget);
      this.myBranchWidget = null;
    }
  }
}

