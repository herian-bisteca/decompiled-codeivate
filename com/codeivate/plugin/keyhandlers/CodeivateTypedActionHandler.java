package com.codeivate.plugin.keyhandlers;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import javax.swing.KeyStroke;
import org.jetbrains.annotations.NotNull;














public class CodeivateTypedActionHandler
  implements TypedActionHandler
{
  private TypedActionHandler origHandler;
  private CodeivateKeyHandler handler;
  private static Logger logger = Logger.getInstance(CodeivateTypedActionHandler.class.getName());
  




  public CodeivateTypedActionHandler(TypedActionHandler origHandler)
  {
    this.origHandler = origHandler;
    this.handler = CodeivateKeyHandler.getInstance();
    this.handler.setOriginalHandler(origHandler);
  }
  



  public TypedActionHandler getOriginalTypedHandler()
  {
    return this.origHandler;
  }
  









  public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext context)
  {
    if (editor == null) throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[] { "editor", "com/codeivate/plugin/keyhandlers/CodeivateTypedActionHandler", "execute" })); if (context == null) throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[] { "context", "com/codeivate/plugin/keyhandlers/CodeivateTypedActionHandler", "execute" })); this.origHandler.execute(editor, charTyped, context);
    try {
      this.handler.handleKey(editor, KeyStroke.getKeyStroke(charTyped), context);
    } catch (Throwable e) {
      logger.error(e);
    }
  }
}
