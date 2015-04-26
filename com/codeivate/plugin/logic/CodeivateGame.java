package com.codeivate.plugin.logic;

import com.codeivate.plugin.CodeivateApplicationPlugin;
import com.codeivate.plugin.data.CodeivatePeriodicUploadItem;
import com.codeivate.plugin.data.CodeivateSettings;
import com.codeivate.plugin.network.CodeivateAPI;
import com.codeivate.plugin.utils.TimeDateUtils;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.Messages;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Observable;
import org.json.simple.JSONObject;








public class CodeivateGame
  extends Observable
{
  public static final int CV_REQUIRED_ACTIONS = 300;
  public static final int CV_STREAK_UNIT = 180;
  public static final int CV_POLL = 50;
  public static final int CV_FOCUS_UNIT = 600;
  public static final String STREAK_STARTED = "Streak Started";
  public static final String STREAK_ENDED = "Streak Ended";
  private static CodeivateGame INSTANCE = null;
  
  protected CodeivatePeriodicUploadItem upload;
  protected long startTime;
  protected long EndTime;
  protected long now;
  protected long streakActions = 0L;
  protected String streakId = "";
  protected String message = "";
  
  protected DateFormat dateFormat;
  
  protected DateFormat timeFormat;
  
  protected DateFormat streakFormat;
  private HashMap<String, String> translationMap;
  private HashMap<String, Integer> pointsMap;
  private static Logger LOG = Logger.getInstance(CodeivateApplicationPlugin.class);
  
  public static CodeivateGame getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new CodeivateGame();
    }
    
    return INSTANCE;
  }
  

  private CodeivateGame()
  {
    this.translationMap = new HashMap() {};
    this.pointsMap = new HashMap();
    
    this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    this.timeFormat = new SimpleDateFormat("HH:mm:ss");
    this.streakFormat = new SimpleDateFormat("yyMMdd");
  }
  
  public void authenticateWithPopups() {
    JSONObject response = new JSONObject();
    try {
      CodeivateAPI codeivateAPI = new CodeivateAPI();
      
      response = codeivateAPI.apiAuthenticate();
      CodeivateSettings codeivateSettings = CodeivateSettings.getInstance();
      
      if (response.get("success") == null)
      {

        if (CodeivateSettings.CODEIVATE_DEBUG.booleanValue()) {
          System.out.println(response.toString());
        }
        Messages.showWarningDialog(response.get("message").toString(), "Codeivate Authentication failure " + response.get("code").toString());
        codeivateSettings.setAuthenticated(Boolean.valueOf(false));
      }
      else {
        Messages.showInfoMessage("You have been successfully authenticated.", "Codeivate Authentication");
        LOG.info("Codeivate Authenticated");
        

        codeivateSettings.setAuthenticated(Boolean.valueOf(true));
      }
    }
    catch (NullPointerException e)
    {
      System.out.println("Error with authenticating!");
      System.out.println(response.toString());
      e.printStackTrace();
    }
  }
  

  public Boolean authenticate()
  {
    JSONObject response = new JSONObject();
    try {
      CodeivateAPI codeivateAPI = new CodeivateAPI();
      
      response = codeivateAPI.apiAuthenticate();
      

      if (response.get("success") == null)
      {

        if (CodeivateSettings.CODEIVATE_DEBUG.booleanValue()) {
          System.out.println(response.toString());
        }
      }
      else
      {
        LOG.info("Codeivate Authenticated");
        
        CodeivateSettings codeivateSettings = CodeivateSettings.getInstance();
        codeivateSettings.setAuthenticated(Boolean.valueOf(true));
        return Boolean.valueOf(true);
      }
    }
    catch (NullPointerException e) {
      System.out.println("Error with authenticating!");
      System.out.println(response.toString());
      e.printStackTrace();
    }
    return Boolean.valueOf(false);
  }
  
  protected String generateStreakID() {
    Calendar cal = Calendar.getInstance();
    int segment = TimeDateUtils.getSegment(cal);
    
    String streakId = this.streakFormat.format(cal.getTime()) + segment;
    
    System.out.println("new streak id = " + streakId);
    
    return streakId;
  }
  
  public void processKeyPressed(char chKey, String langStr, String l1, String l2)
  {
    if ((langStr.equals("")) || (langStr.equals("TEXT"))) {
      langStr = "Plain Text";
    }
    
    Calendar cal = Calendar.getInstance();
    this.now = (System.currentTimeMillis() / 1000L);
    
    boolean postal = false;
    boolean streakEnd = false;
    long timeDiffStreak = 0L;
    
    int segment = TimeDateUtils.getSegment(cal);
    



    if (this.startTime == 0L) {
      this.startTime = (System.currentTimeMillis() / 1000L);
      this.streakId = generateStreakID();
      setMessage("Streak Started");
    } else {
      long timeDiffNow = this.now - this.EndTime;
      timeDiffStreak = this.EndTime - this.startTime;
      

      if (timeDiffNow > 180L) {
        this.startTime = (System.currentTimeMillis() / 1000L);
        System.out.println("Streak ended");
        postal = true;
        streakEnd = true;
      }
    }
    

    this.EndTime = (System.currentTimeMillis() / 1000L);
    





    Integer value = Integer.valueOf(1);
    
    if (!this.pointsMap.containsKey(langStr)) {
      this.pointsMap.put(langStr, value);
    } else {
      value = (Integer)this.pointsMap.get(langStr);
      Integer localInteger1 = value;Integer localInteger2 = value = Integer.valueOf(value.intValue() + 1);
      this.pointsMap.put(langStr, value);
    }
    

    setMessage(langStr + " " + TimeDateUtils.getDurationString((int)timeDiffStreak));
    
    this.streakActions += 1L;
    
    if (value.intValue() % 50 == 0) {
      postal = true;
    }
    

    if (postal)
    {

      setMessage("Uploading");
      
      this.upload = CodeivatePeriodicUploadItem.createCodeivateUpload();
      
      this.upload.setDate(this.dateFormat.format(cal.getTime()));
      this.upload.setTime(this.timeFormat.format(cal.getTime()));
      this.upload.setSegment(segment);
      this.upload.setLang(langStr);
      this.upload.setAction(String.valueOf(value));
      this.upload.setStreak_length(TimeDateUtils.getDurationString((int)timeDiffStreak));
      this.upload.setStreakcount(String.valueOf(this.streakActions));
      this.upload.setStreak_id(String.valueOf(this.streakId));
      

      if (CodeivateSettings.CODEIVATE_DEBUG.booleanValue()) {
        System.out.println("date:" + this.upload.getDate());
        System.out.println("time:" + this.upload.getTime());
        System.out.println("segment: " + this.upload.getSegment());
        System.out.println("lang:" + this.upload.getLang());
        System.out.println("actions:" + this.upload.getAction());
        System.out.println("streak_length :" + this.upload.getStreak_length());
        System.out.println("streakcount:" + this.upload.getStreakcount());
        System.out.println("streak_id:" + this.upload.getStreak_id());
      }
      

      new CodeivateBusiness(this.upload).start();
      
      LOG.debug("posting");
      

      this.pointsMap.put(langStr, Integer.valueOf(0));
      
      if (streakEnd) {
        this.streakActions = 0L;
        this.streakId = generateStreakID();
        setMessage("Streak Ended");
      }
    }
  }
  
  public boolean hasMessage() {
    return !this.message.equals("");
  }
  
  private void setMessage(String message) {
    this.message = message;
    setChanged();
    notifyObservers(message);
  }
  
  public String getMessage() {
    String m = this.message;
    this.message = "";
    return m;
  }
}
