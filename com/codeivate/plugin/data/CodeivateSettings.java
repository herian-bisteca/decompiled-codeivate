package com.codeivate.plugin.data;

import com.codeivate.plugin.utils.UUIDUtils;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.application.ApplicationInfo;
import java.io.PrintStream;
import java.util.Observable;







public class CodeivateSettings
  extends Observable
{
  public static final Boolean CODEIVATE_DEBUG = Boolean.valueOf(false);
  
  public static final String URL_CODEIVATE_API = "http://162.243.127.15/API/index.php";
  
  public static final String URL_CODEIVATE_API_TOKEN_AUTH = "http://162.243.127.15/API/index.php?action=token";
  
  private String version;
  private String platform;
  private String user_name;
  private String user_id;
  private String user_token;
  private String machine_id;
  private String machine_name;
  private String codeivate_version;
  private String ide_name;
  private PropertiesComponent instance;
  private static CodeivateSettings INSTANCE = null;
  
  private Boolean authenticated = Boolean.valueOf(false);
  
  private CodeivateSettings()
  {
    this.instance = PropertiesComponent.getInstance();
    

    setVersion();
    setIDEName();
    setPlatform();
    


    String machineId = UUIDUtils.getUniqueMachineId();
    setMachine_id(machineId);
    System.out.println("Setting machine id " + machineId);
    

    if ((getMachine_name() == null) || (getMachine_name().equals("")))
    {
      String machineName = UUIDUtils.getRandomName();
      setMachine_name(machineName);
      System.out.println("Setting machine name " + machineName);
    }
    
    if ((getUser_id() == "") && (getUser_token() == "")) {
      setAuthenticated(Boolean.valueOf(true));
    }
  }
  
  public static CodeivateSettings getInstance()
  {
    if (INSTANCE == null) {
      INSTANCE = new CodeivateSettings();
    }
    return INSTANCE;
  }
  


  public String getVersion()
  {
    return this.version;
  }
  
  private void setVersion() {
    this.version = ApplicationInfo.getInstance().getFullVersion();
  }
  
  private void setIDEName() {
    this.ide_name = ApplicationInfo.getInstance().getVersionName();
  }
  
  public String getPlatform() {
    return this.platform;
  }
  
  private void setPlatform() {
    String osname = System.getProperty("os.name");
    String platform;
    String platform;
    if (osname.startsWith("Windows")) {
      platform = "windows"; } else { String platform;
      if (osname.startsWith("Mac")) {
        platform = "osx";
      }
      else
        platform = "linux";
    }
    this.platform = platform;
  }
  
  public String getCodeivate_version() {
    return this.codeivate_version;
  }
  
  public String getIde() {
    return this.ide_name;
  }
  
  public void setCodeivate_version(String codeivate_version) {
    this.codeivate_version = codeivate_version;
  }
  


  public String getUser_name()
  {
    try
    {
      this.user_name = this.instance.getValue("user_name").trim();
    } catch (NullPointerException $e) {
      this.user_name = "";
    }
    return this.user_name;
  }
  
  public void setUser_name(String user_name) {
    this.user_name = user_name;
    this.instance.setValue("user_name", user_name);
    setChanged();
    notifyObservers("user_name");
  }
  
  public String getUser_id()
  {
    try {
      this.user_id = this.instance.getValue("user_id").trim();
    } catch (NullPointerException $e) {
      this.user_id = "";
    }
    return this.user_id;
  }
  
  public void setUser_id(String user_id) {
    this.user_id = user_id;
    this.instance.setValue("user_id", user_id);
    setChanged();
    notifyObservers("user_id");
  }
  
  public String getUser_token() {
    try {
      this.user_token = this.instance.getValue("user_token").trim();
    } catch (NullPointerException $e) {
      this.user_token = "";
    }
    return this.user_token;
  }
  
  public void setUser_token(String user_token) {
    this.user_token = user_token;
    this.instance.setValue("user_token", user_token);
    setChanged();
    notifyObservers("user_token");
  }
  
  public String getMachine_id() {
    try {
      this.machine_id = this.instance.getValue("machine_id");
    } catch (NullPointerException $e) {
      this.machine_id = "";
    }
    return this.machine_id;
  }
  




  public void setMachine_id(String machine_id)
  {
    this.machine_id = machine_id;
    this.instance.setValue("machine_id", machine_id);
  }
  
  public String getMachine_name()
  {
    try
    {
      this.machine_name = this.instance.getValue("machine_name").trim();
    } catch (NullPointerException $e) {
      this.machine_name = "";
    }
    return this.machine_name;
  }
  
  public void setMachine_name(String machine_name) {
    this.machine_name = machine_name;
    this.instance.setValue("machine_name", machine_name);
  }
  


  public void setAuthenticated(Boolean authed)
  {
    this.authenticated = authed;
    setChanged();
    notifyObservers("authenticated");
  }
  
  public Boolean getAuthenticated()
  {
    return this.authenticated;
  }
}