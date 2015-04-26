package com.codeivate.plugin.utils;

import com.codeivate.plugin.data.CodeivateSettings;
import com.codeivate.plugin.network.CodeivateAPI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;






public class UUIDUtils
{
  public static String getMacAddress()
    throws Exception
  {
    String macAddress = null;
    

    String osName = System.getProperty("os.name");
    
    if (CodeivateSettings.CODEIVATE_DEBUG.booleanValue()) {
      System.out.println("Operating System is " + osName);
    }
    String command;
    if (osName.startsWith("Windows")) {
      command = "ipconfig /all"; } else { String command;
      if ((osName.startsWith("Linux")) || (osName.startsWith("Mac")) || (osName.startsWith("HP-UX")) || (osName.startsWith("NeXTStep")) || (osName.startsWith("Solaris")) || (osName.startsWith("SunOS")) || (osName.startsWith("FreeBSD")) || (osName.startsWith("NetBSD")))
      {

        command = "ifconfig -a"; } else { String command;
        if (osName.startsWith("OpenBSD")) {
          command = "netstat -in"; } else { String command;
          if ((osName.startsWith("IRIX")) || (osName.startsWith("AIX")) || (osName.startsWith("Tru64"))) {
            command = "netstat -ia"; } else { String command;
            if ((osName.startsWith("Caldera")) || (osName.startsWith("UnixWare")) || (osName.startsWith("OpenUNIX"))) {
              command = "ndstat";
            } else
              throw new Exception("The current operating system '" + osName + "' is not supported.");
          } } } }
    String command;
    Process pid = Runtime.getRuntime().exec(command);
    BufferedReader in = new BufferedReader(new InputStreamReader(pid.getInputStream()));
    Pattern p = Pattern.compile("([\\w]{1,2}(-|:)){5}[\\w]{1,2}");
    for (;;) {
      String line = in.readLine();
      
      if (CodeivateSettings.CODEIVATE_DEBUG.booleanValue()) {
        System.out.println("line " + line);
      }
      if (line == null) {
        break;
      }
      Matcher m = p.matcher(line);
      if (m.find()) {
        macAddress = m.group();
        break;
      }
    }
    in.close();
    return macAddress;
  }
  
  private static String getUUID() {
    UUID uuid;
    try {
      uuid = UUID.nameUUIDFromBytes(getMacAddress().getBytes());
    }
    catch (Exception e) {
      if (CodeivateSettings.CODEIVATE_DEBUG.booleanValue()) {
        e.printStackTrace();
      }
      uuid = UUID.randomUUID();
    }
    
    return uuid.toString();
  }
  

  public static String getUniqueMachineId()
  {
    String machine_id = getUUID();
    
    return new BigInteger(machine_id.replaceAll("-", ""), 16).toString(36);
  }
  
  public static String getRandomName()
  {
    String url = "http://namey.muffinlabs.com/name.json?count=1&with_surname=true&frequency=all";
    String new_random_name = "change_me";
    
    try
    {
      CodeivateAPI codeivateAPI = new CodeivateAPI("GET", url);
      String result = codeivateAPI.process();
      Object resultObj = JSONValue.parse(result);
      JSONArray array = (JSONArray)resultObj;
      new_random_name = array.get(0).toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    if (CodeivateSettings.CODEIVATE_DEBUG.booleanValue()) {
      System.out.println("Setting this machines name: " + new_random_name);
    }
    return new_random_name;
  }
}
