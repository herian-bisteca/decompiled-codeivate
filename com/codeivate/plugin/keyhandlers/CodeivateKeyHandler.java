package com.codeivate.plugin.keyhandlers;

import com.codeivate.plugin.CodeivateApplicationPlugin;
import com.codeivate.plugin.data.CodeivateSettings;
import com.codeivate.plugin.logic.CodeivateGame;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import java.util.List;
import javax.swing.KeyStroke;
import org.jetbrains.annotations.NotNull;




public class CodeivateKeyHandler
{
  private static Logger LOG = Logger.getInstance(CodeivateApplicationPlugin.class);
  

  private int count;
  
  private List<KeyStroke> keys;
  
  private TypedActionHandler origHandler;
  
  private char lastChar;
  
  private boolean lastWasBS;
  
  private static StatusBar statusBar;
  
  private static CodeivateSettings codeivateSettings;
  
  private static CodeivateKeyHandler instance;
  
  private CodeivateGame codeivateGame;
  

  @NotNull
  public static CodeivateKeyHandler getInstance()
  {
    if (instance == null) {
      instance = new CodeivateKeyHandler();
    }
    
    CodeivateKeyHandler tmp19_16 = instance;
    

























































    if (tmp19_16 == null) throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[] { "com/codeivate/plugin/keyhandlers/CodeivateKeyHandler", "getInstance" })); return tmp19_16;
  }
  



  private CodeivateKeyHandler()
  {
    this.codeivateGame = CodeivateGame.getInstance();
    codeivateSettings = CodeivateSettings.getInstance();
  }
  




  public void setOriginalHandler(TypedActionHandler origHandler)
  {
    this.origHandler = origHandler;
  }
  




  public TypedActionHandler getOriginalHandler()
  {
    return this.origHandler;
  }
  








  public void handleKey(@NotNull Editor editor, @NotNull KeyStroke key, @NotNull DataContext context)
  {
    if (editor == null) throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[] { "editor", "com/codeivate/plugin/keyhandlers/CodeivateKeyHandler", "handleKey" })); if (key == null) throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[] { "key", "com/codeivate/plugin/keyhandlers/CodeivateKeyHandler", "handleKey" })); if (context == null) throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[] { "context", "com/codeivate/plugin/keyhandlers/CodeivateKeyHandler", "handleKey" })); StatusBar statusBar = WindowManager.getInstance().getStatusBar(editor.getProject());
    



    try
    {
      if (!codeivateSettings.getAuthenticated().booleanValue()) {
        codeivateSettings.notifyObservers("Not authenticated");
        return;
      }
      



      Project project = editor.getProject();
      Document document = editor.getDocument();
      
      PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
      int offset = editor.getCaretModel().getOffset();
      PsiElement element = psiFile.findElementAt(offset);
      
      char chKey = key.getKeyChar() == 65535 ? '\000' : key.getKeyChar();
      
      String langStr = "";
      String langAlt = "";
      String l1 = "";
      String l2 = "";
      if (element != null) {
        langStr = element.getLanguage().toString().substring(10);
        
        if (langStr.equals("XML")) {
          langAlt = psiFile.getLanguage().toString().substring(10);
          
          if (!langAlt.equals(langStr)) {
            langStr = langAlt;
          }
        } else if ((langStr.equals("JavaScript")) || (langStr.equals("ECMA Script Level 4"))) {
          try {
            if (((EditorImpl)editor).getVirtualFile().getExtension().equals("as")) {
              langStr = "ActionScript";


            }
            


          }
          catch (Exception e) {}

        }
        


      }
      else
      {


        try
        {


          langStr = psiFile.getLanguage().toString().substring(10);
          
          if ((langStr.equals("JavaScript")) || (langStr.equals("ECMA Script Level 4"))) {
            try
            {
              if (((EditorImpl)editor).getVirtualFile().getExtension().equals("as")) {
                langStr = "ActionScript";
              }
            }
            catch (Exception e) {}
          }
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
      


      this.codeivateGame.processKeyPressed(chKey, langStr, l1, l2);




























































    }
    catch (Exception e)
    {



























































      LOG.debug("Keyhandler not ready");
      LOG.debug(e.getMessage());
    }
  }
}