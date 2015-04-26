package com.codeivate.plugin.logic;

import com.codeivate.plugin.CodeivateApplicationPlugin;
import com.codeivate.plugin.data.CodeivatePeriodicUploadItem;
import com.codeivate.plugin.network.CodeivateAPI;
import com.intellij.openapi.diagnostic.Logger;
import java.io.PrintStream;








public class CodeivateBusiness
  extends Thread
{
  private static Logger LOG = Logger.getInstance(CodeivateApplicationPlugin.class);
  
  private CodeivateAPI codeivateAPI;
  
  private static int counter = 0;
  private CodeivatePeriodicUploadItem upload;
  
  public CodeivateBusiness(CodeivatePeriodicUploadItem parcel)
  {
    this.upload = parcel;
    counter += 1;
    System.out.println("Codeivate Business thread started{" + counter + "}");
  }
  
  public void run()
  {
    LOG.debug("posting");
    try
    {
      String method = "POST";
      String url = "http://162.243.127.15/API/index.php";
      





      this.codeivateAPI = new CodeivateAPI(method, url, this.upload.toPostMap());
    }
    catch (Exception ex) {}
  }
}
