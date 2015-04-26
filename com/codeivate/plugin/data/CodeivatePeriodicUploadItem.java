package com.codeivate.plugin.data;

import com.intellij.openapi.application.ApplicationInfo;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.simple.JSONObject;













public class CodeivatePeriodicUploadItem
{
  private String date;
  private String time;
  private int segment;
  private String lang;
  private String scope_lang;
  private String action;
  private String streakcount;
  private String streak_length;
  private String streak_id;
  private String version;
  private String platform;
  private String user_id;
  private String user_token;
  private String machine_id;
  private String machine_name;
  private String codeivate_version;
  private String ide_name;
  
  public static CodeivatePeriodicUploadItem createCodeivateUpload()
  {
    CodeivateSettings settings = CodeivateSettings.getInstance();
    CodeivatePeriodicUploadItem codeivatePeriodicUploadItem = new CodeivatePeriodicUploadItem();
    
    codeivatePeriodicUploadItem.setMachine_name(settings.getMachine_name());
    codeivatePeriodicUploadItem.setMachine_id(settings.getMachine_id());
    codeivatePeriodicUploadItem.setCodeivate_version(settings.getCodeivate_version());
    codeivatePeriodicUploadItem.setCodeivate_version(settings.getCodeivate_version());
    codeivatePeriodicUploadItem.setUser_id(settings.getUser_id());
    codeivatePeriodicUploadItem.setUser_token(settings.getUser_token());
    codeivatePeriodicUploadItem.setIde(settings.getIde());
    
    return codeivatePeriodicUploadItem;
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date) {
    this.date = date;
  }
  
  public String getTime() {
    return this.time;
  }
  
  public void setTime(String time) {
    this.time = time;
  }
  
  public int getSegment() {
    return this.segment;
  }
  
  public void setSegment(int segment) {
    this.segment = segment;
  }
  
  public String getLang() {
    return this.lang;
  }
  
  public void setLang(String lang) {
    this.lang = lang;
  }
  
  public String getScope_lang() {
    return this.scope_lang;
  }
  
  public void setScope_lang(String scope_lang) {
    this.scope_lang = scope_lang;
  }
  
  public String getAction() {
    return this.action;
  }
  
  public void setAction(String action) {
    this.action = action;
  }
  
  public String getStreakcount() {
    return this.streakcount;
  }
  
  public void setStreakcount(String streakcount) {
    this.streakcount = streakcount;
  }
  
  public String getStreak_length() {
    return this.streak_length;
  }
  
  public void setStreak_length(String streak_length) {
    this.streak_length = streak_length;
  }
  
  public String getStreak_id() {
    return this.streak_id;
  }
  
  public void setStreak_id(String streak_id) {
    this.streak_id = streak_id;
  }
  
  public String getVersion()
  {
    return ApplicationInfo.getInstance().getFullVersion();
  }
  



  public String getPlatform()
  {
    return System.getProperty("os.name");
  }
  



  public String getUser_id()
  {
    return this.user_id;
  }
  
  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }
  
  public String getUser_token() {
    return this.user_token;
  }
  
  public void setUser_token(String user_token) {
    this.user_token = user_token;
  }
  
  public String getMachine_id() {
    return this.machine_id;
  }
  
  public void setMachine_id(String machine_id) {
    this.machine_id = machine_id;
  }
  
  public String getMachine_name() {
    return this.machine_name;
  }
  
  public void setMachine_name(String machine_name) {
    this.machine_name = machine_name;
  }
  
  public String getCodeivate_version() {
    return this.codeivate_version;
  }
  
  public void setCodeivate_version(String codeivate_version) {
    this.codeivate_version = codeivate_version;
  }
  
  public void setIde(String ide_name) {
    this.ide_name = ide_name;
  }
  
  public String getIde() {
    return this.ide_name;
  }
  
  public String toJSONString()
  {
    JSONObject obj = new JSONObject();
    
    obj.put("date", getDate());
    obj.put("time", getTime());
    obj.put("segment", Integer.valueOf(getSegment()));
    obj.put("lang", getLang());
    obj.put("scope_lang", getScope_lang());
    obj.put("action", getAction());
    obj.put("streakcount", getStreakcount());
    obj.put("streak_length", getStreak_length());
    obj.put("streak_id", getStreak_id());
    obj.put("version", getVersion());
    obj.put("platform", getPlatform());
    obj.put("user_id", getUser_id());
    obj.put("user_token", getUser_token());
    obj.put("machine_id", getMachine_id());
    obj.put("machine_name", getMachine_name());
    obj.put("codeivate_version", getCodeivate_version());
    obj.put("ide_name", getIde());
    
    return obj.toJSONString();
  }
  
  public StringBuilder toPostMap() throws UnsupportedEncodingException {
    Map<String, Object> obj = new LinkedHashMap();
    
    obj.put("date", getDate());
    obj.put("time", getTime());
    obj.put("segment", Integer.valueOf(getSegment()));
    obj.put("lang", getLang());
    obj.put("scope_lang", getScope_lang());
    obj.put("action", getAction());
    obj.put("streakcount", getStreakcount());
    obj.put("streak_length", getStreak_length());
    obj.put("streak_id", getStreak_id());
    obj.put("version", getVersion());
    obj.put("platform", getPlatform());
    obj.put("user_id", getUser_id());
    obj.put("user_token", getUser_token());
    obj.put("machine_id", getMachine_id());
    obj.put("machine_name", getMachine_name());
    obj.put("codeivate_version", getCodeivate_version());
    obj.put("ide_name", getIde());
    
    StringBuilder postData = new StringBuilder();
    for (Map.Entry<String, Object> param : obj.entrySet()) {
      if (postData.length() != 0) postData.append('&');
      postData.append(URLEncoder.encode((String)param.getKey(), "UTF-8"));
      postData.append('=');
      postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
    }
    return postData;
  }
}
