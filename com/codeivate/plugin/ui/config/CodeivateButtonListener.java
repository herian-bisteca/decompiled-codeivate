package com.codeivate.plugin.ui.config;

import com.codeivate.plugin.data.CodeivateSettings;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;












@Deprecated
public class CodeivateButtonListener
  implements ActionListener
{
  public JTextField user_id;
  public JTextField user_token;
  public JTextField machine_name;
  private CodeivateSettings codeivateSettings = CodeivateSettings.getInstance();
  





  public void actionPerformed(ActionEvent e)
  {
    this.user_id.setText(this.codeivateSettings.getUser_id());
    this.user_token.setText(this.codeivateSettings.getUser_token());
    this.machine_name.setText(this.codeivateSettings.getMachine_name());
  }
}
