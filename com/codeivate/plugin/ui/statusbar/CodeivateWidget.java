package com.codeivate.plugin.ui.statusbar;

import com.codeivate.plugin.data.CodeivateSettings;
import com.codeivate.plugin.logic.CodeivateGame;
import com.codeivate.plugin.ui.config.CodeivateConfigurable;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget.PlatformType;
import com.intellij.openapi.wm.StatusBarWidget.TextPresentation;
import com.intellij.openapi.wm.StatusBarWidget.WidgetPresentation;
import com.intellij.openapi.wm.impl.status.EditorBasedWidget;
import com.intellij.util.Consumer;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;





public class CodeivateWidget
  extends EditorBasedWidget
  implements StatusBarWidget.TextPresentation, Observer
{
  private volatile String myText = "Welcome to Codeivate";
  private volatile String myTooltip = "";
  private final String myMaxString;
  
  public CodeivateWidget(Project project) {
    super(project);
    this.myMaxString = "Welcome to Codeivate";
    
    CodeivateSettings codeivateSettings = CodeivateSettings.getInstance();
    codeivateSettings.addObserver(this);
    
    CodeivateGame.getInstance().addObserver(this);
  }
  

  @NotNull
  public String ID()
  {
    String tmp5_2 = CodeivateWidget.class.getName();
    














































    if (tmp5_2 == null) throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[] { "com/codeivate/plugin/ui/statusbar/CodeivateWidget", "ID" })); return tmp5_2;
  }
  
  public StatusBarWidget.WidgetPresentation getPresentation(@NotNull StatusBarWidget.PlatformType type)
  {
    if (type == null) throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[] { "type", "com/codeivate/plugin/ui/statusbar/CodeivateWidget", "getPresentation" })); return this;
  }
  

  @NotNull
  public String getText()
  {
    String tmp4_1 = this.myText;
    


























































    if (tmp4_1 == null) throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[] { "com/codeivate/plugin/ui/statusbar/CodeivateWidget", "getText" })); return tmp4_1;
  }
  
  @NotNull
  public String getMaxPossibleText()
  {
    String tmp4_1 = this.myMaxString;
    
































































    if (tmp4_1 == null) throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[] { "com/codeivate/plugin/ui/statusbar/CodeivateWidget", "getMaxPossibleText" })); return tmp4_1;
  }
  
  public float getAlignment()
  {
    return 0.0F;
  }
  
  @Nullable
  public String getTooltipText()
  {
    return this.myTooltip;
  }
  
  public void showSettings()
  {
    ApplicationManager.getApplication().invokeLater(new Runnable()
    {
      public void run() {
        Project project = CodeivateWidget.this.getProject();
        if (project == null) {
          CodeivateWidget.this.emptyTextAndTooltip();
          return;
        }
        ShowSettingsUtil.getInstance().editConfigurable(project, CodeivateConfigurable.getInstance());
      }
    });
  }
  

  @Nullable
  public Consumer<MouseEvent> getClickConsumer()
  {
    new Consumer() {
      public void consume(MouseEvent mouseEvent) {
        CodeivateWidget.this.showSettings();
      }
    };
  }
  
  private void emptyTextAndTooltip() {
    this.myText = "Welcome to Codeivate";
    this.myTooltip = "Codeivate";
  }
  
  private void update() {
    ApplicationManager.getApplication().invokeLater(new Runnable()
    {
      public void run() {
        Project project = CodeivateWidget.this.getProject();
        if (project == null) {
          CodeivateWidget.this.emptyTextAndTooltip();
          return;
        }
        


        CodeivateWidget.this.myStatusBar.updateWidget(CodeivateWidget.this.ID());
      }
    });
  }
  

  public void update(Observable o, Object arg)
  {
    this.myText = ((String)arg);
    update();
  }
}
