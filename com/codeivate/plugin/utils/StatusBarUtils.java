package com.codeivate.plugin.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.WindowManager;
import org.jetbrains.annotations.NotNull;

public class StatusBarUtils
{
  public static void installStatusBarWidget(@NotNull Project project, @NotNull StatusBarWidget widget)
  {
    if (project == null) throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[] { "project", "com/codeivate/plugin/utils/StatusBarUtils", "installStatusBarWidget" })); if (widget == null) throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[] { "widget", "com/codeivate/plugin/utils/StatusBarUtils", "installStatusBarWidget" })); StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
    if (statusBar != null) {
      statusBar.addWidget(widget, "after " + (SystemInfo.isMac ? "Encoding" : "InsertOverwrite"), project);
    }
  }
  
  public static void removeStatusBarWidget(@NotNull Project project, @NotNull StatusBarWidget widget) {
    if (project == null) throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[] { "project", "com/codeivate/plugin/utils/StatusBarUtils", "removeStatusBarWidget" })); if (widget == null) throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[] { "widget", "com/codeivate/plugin/utils/StatusBarUtils", "removeStatusBarWidget" })); StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
    if (statusBar != null) {
      statusBar.removeWidget(widget.ID());
    }
  }
}
