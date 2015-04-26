package com.codeivate.plugin.ui.config;

import com.codeivate.plugin.data.CodeivateSettings;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;

public class CodeivateConfigurable implements com.intellij.openapi.options.Configurable
{
  private javax.swing.JComponent myComponent;
  private CodeivateSettings codeivateSettings;
  private javax.swing.JPanel codeivatePanel;
  private JLabel title;
  private JTextField user_id;
  private JTextField user_token;
  private JTextField machine_name;
  
  public CodeivateConfigurable()
  {
    $$$setupUI$$$();
  }
  








  private static CodeivateConfigurable INSTANCE = null;
  private JLabel required;
  private JTextField user_name;
  private JEditorPane install_instructions;
  private JLabel authenticated;
  private JLabel imgSignature;
  private javax.swing.JButton saveButton;
  
  @org.jetbrains.annotations.NotNull
  public static CodeivateConfigurable getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new CodeivateConfigurable();
    }
    CodeivateConfigurable tmp19_16 = INSTANCE;
    















































    if (tmp19_16 == null) throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[] { "com/codeivate/plugin/ui/config/CodeivateConfigurable", "getInstance" })); return tmp19_16;
  }
  
  public String getDisplayName() {
    return "Codeivate Settings";
  }
  


  public boolean isModified()
  {
    if (this.myComponent == null) {
      return false;
    }
    
    if ((this.codeivateSettings.getUser_name() == null) || (!this.codeivateSettings.getUser_name().equals(this.user_name.getText()))) {
      return true;
    }
    
    if ((this.codeivateSettings.getUser_id() == null) || (!this.codeivateSettings.getUser_id().equals(this.user_id.getText()))) {
      return true;
    }
    if ((this.codeivateSettings.getUser_token() == null) || (!this.codeivateSettings.getUser_token().equals(this.user_token.getText()))) {
      return true;
    }
    if ((this.codeivateSettings.getMachine_name() == null) || (!this.codeivateSettings.getMachine_name().equals(this.machine_name.getText()))) {
      return true;
    }
    return false;
  }
  
  protected void updateAuthenticated()
  {
    if (this.codeivateSettings.getAuthenticated().booleanValue()) {
      this.authenticated.setText("Authenticated");
      this.authenticated.setForeground(Color.BLACK);
      this.required.setForeground(Color.BLACK);
    } else {
      this.authenticated.setText("Not Authenticated");
      this.authenticated.setForeground(Color.RED);
      this.required.setForeground(Color.RED);
    }
  }
  

  protected void updateDescriptionPanel()
  {
    if ((this.codeivateSettings.getAuthenticated().booleanValue()) && (this.codeivateSettings.getUser_name() != null) && (this.codeivateSettings.getUser_name().length() > 0))
    {

      String username = this.codeivateSettings.getUser_name();
      try {
        String path = "http://www.codeivate.com/users/" + username + "/signature.png?from=intellij";
        URL signatureUrl = new URL(path);
        java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(signatureUrl);
        
        this.imgSignature.setSize(562, 119);
        this.imgSignature.setIcon(new javax.swing.ImageIcon(image));
        this.imgSignature.addMouseListener(new java.awt.event.MouseListener() {
          public void mouseClicked(MouseEvent arg0) {
            Desktop desktop = Desktop.getDesktop();
            try {
              URL summary = new URL("http://www.codeivate.com/summary");
              desktop.browse(summary.toURI());
            } catch (Exception ex) {
              ex.printStackTrace();
            }
          }
          





          public void mousePressed(MouseEvent e) {}
          




          public void mouseReleased(MouseEvent e) {}
          




          public void mouseEntered(MouseEvent e) {}
          




          public void mouseExited(MouseEvent e) {}
        });
      }
      catch (Exception exp) {}
    }
  }
  




  public javax.swing.JComponent createComponent()
  {
    this.codeivateSettings = CodeivateSettings.getInstance();
    






    this.user_name.setText(this.codeivateSettings.getUser_name());
    this.user_id.setText(this.codeivateSettings.getUser_id());
    this.user_token.setText(this.codeivateSettings.getUser_token());
    this.machine_name.setText(this.codeivateSettings.getMachine_name());
    
    updateDescriptionPanel();
    


    String htmlInstallInstructions = "<html><head><style>a {color:blue;}</style><body><div><h3>Install instructions</h3><ol><li>Sign up at <a href='http://www.codeivate.com/'>http://www.codeivate.com/</a></li><li>Get your user_id and token from <a href='http://www.codeivate.com/token'>http://www.codeivate.com/token</a></li></ol></div> </body></html>";
    













    this.install_instructions.setOpaque(false);
    this.install_instructions.setText(htmlInstallInstructions);
    this.install_instructions.addHyperlinkListener(new javax.swing.event.HyperlinkListener()
    {
      public void hyperlinkUpdate(HyperlinkEvent hle) {
        if (javax.swing.event.HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
          System.out.println(hle.getURL());
          Desktop desktop = Desktop.getDesktop();
          try {
            desktop.browse(hle.getURL().toURI());
          } catch (Exception ex) {
            ex.printStackTrace();
          }
          
        }
      }
    });
    updateAuthenticated();
    this.myComponent = this.codeivatePanel;
    return this.myComponent;
  }
  
  public javax.swing.Icon getIcon()
  {
    return null;
  }
  



  public void apply()
  {
    String _user_name = this.user_name.getText();
    String _user_id = this.user_id.getText();
    String _user_token = this.user_token.getText();
    String _machine_name = this.machine_name.getText();
    


    this.codeivateSettings.setUser_name(_user_name);
    this.codeivateSettings.setUser_id(_user_id);
    this.codeivateSettings.setUser_token(_user_token);
    this.codeivateSettings.setMachine_name(_machine_name);
    
    updateDescriptionPanel();
    com.codeivate.plugin.logic.CodeivateGame.getInstance().authenticateWithPopups();
    updateAuthenticated();
  }
  



  public void disposeUIResources() {}
  


  public String getHelpTopic()
  {
    return null;
  }
  
  public void reset() {}
  
  private void createUIComponents() {}
}
