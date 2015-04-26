package com.codeivate.plugin.network;

import com.codeivate.plugin.CodeivateApplicationPlugin;
import com.codeivate.plugin.data.CodeivateSettings;
import com.intellij.openapi.diagnostic.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
































public class CodeivateAPI
{
  private String url;
  private String method;
  private String jsonString;
  private String postString;
  private final HttpClient httpClient = new HttpClient();
  private HttpMethod httpMethod;
  private static Logger LOG = Logger.getInstance(CodeivateApplicationPlugin.class);
  
  public CodeivateAPI() {}
  
  public CodeivateAPI(String method, String url)
    throws UnsupportedEncodingException
  {
    this.method = method;
    this.url = url;
    
    this.httpMethod = getMethod();
  }
  
  public CodeivateAPI(String method, String url, String jsonString) throws UnsupportedEncodingException {
    this.method = method;
    this.url = url;
    this.jsonString = jsonString;
    
    this.httpMethod = getMethod();
  }
  
  public CodeivateAPI(String method, String url, StringBuilder postString) throws UnsupportedEncodingException {
    this.method = method;
    this.url = url;
    this.postString = postString.toString();
    
    apiMethod();
  }
  
  public JSONObject apiAuthenticate() {
    CodeivateSettings codeivateSettings = CodeivateSettings.getInstance();
    
    LOG.info("Initialising Codeivate " + codeivateSettings.getCodeivate_version());
    

    JSONObject response = new JSONObject();
    URL url = null;
    try {
      url = new URL("http://www.codeivate.com/API/index.php?action=token");
      
      Map<String, Object> params = new LinkedHashMap();
      
      params.put("user_id", codeivateSettings.getUser_id());
      params.put("user_token", codeivateSettings.getUser_token());
      params.put("action", "token");
      params.put("platform", codeivateSettings.getPlatform());
      params.put("arch", "");
      params.put("version", codeivateSettings.getVersion());
      params.put("codeivate_version", codeivateSettings.getCodeivate_version());
      params.put("ide_name", codeivateSettings.getIde());
      StringBuilder postData = new StringBuilder();
      for (Map.Entry<String, Object> param : params.entrySet()) {
        if (postData.length() != 0) postData.append('&');
        postData.append(URLEncoder.encode((String)param.getKey(), "UTF-8"));
        postData.append('=');
        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
      }
      byte[] postDataBytes = postData.toString().getBytes("UTF-8");
      
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
      conn.setDoOutput(true);
      conn.getOutputStream().write(postDataBytes);
      
      Reader in = null;
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      Object jsonResponse = JSONValue.parse(in);
      
      response = (JSONObject)jsonResponse;
    }
    catch (IOException e) {
      System.out.println("Auth test failed!");
      e.printStackTrace();
    }
    return response;
  }
  


  public void apiMethod()
  {
    JSONObject jo = new JSONObject();
    URL url = null;
    try {
      url = new URL("http://162.243.127.15/API/index.php");
      
      byte[] postDataBytes = this.postString.getBytes("UTF-8");
      
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
      conn.setDoOutput(true);
      conn.getOutputStream().write(postDataBytes);
      
      Reader in = null;
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      Object jsonResponse = JSONValue.parse(in);
      JSONObject response = (JSONObject)jsonResponse;
      

      try
      {
        System.out.println(response.toString());
      }
      catch (NullPointerException e) {
        System.out.println("Error with authenticating!");
        System.out.println(response.toString());
      }
    }
    catch (IOException e) {
      System.out.println("Auth test failed!");
      e.printStackTrace();
    }
  }
  


  @Deprecated
  public void post()
    throws IOException
  {
    URL url = new URL("http://162.243.127.15/API/index.php?action=token");
    Map<String, Object> params = new LinkedHashMap();
    params.put("name", "Freddie the Fish");
    params.put("email", "fishie@seemail.com");
    params.put("reply_to_thread", Integer.valueOf(10394));
    params.put("message", "Shark attacks in Botany Bay have gotten out of control. We need more defensive dolphins to protect the schools here, but Mayor Porpoise is too busy stuffing his snout with lobsters. He's so shellfish.");
    
    StringBuilder postData = new StringBuilder();
    for (Map.Entry<String, Object> param : params.entrySet()) {
      if (postData.length() != 0) postData.append('&');
      postData.append(URLEncoder.encode((String)param.getKey(), "UTF-8"));
      postData.append('=');
      postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
    }
    byte[] postDataBytes = postData.toString().getBytes("UTF-8");
    
    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
    conn.setDoOutput(true);
    conn.getOutputStream().write(postDataBytes);
    
    Reader in = null;
    in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
    int c; while ((c = in.read()) >= 0) { System.out.print((char)c);
    }
  }
  














  public HttpMethod getMethod()
    throws UnsupportedEncodingException
  {
    if ("GET".equals(this.method))
      return new GetMethod(this.url);
    if ("POST".equals(this.method)) {
      PostMethod postMethod = new PostMethod(this.url);
      postMethod.setRequestEntity(new StringRequestEntity(this.jsonString, "application/json", "UTF-8"));
      
      return postMethod;
    }
    throw new IllegalStateException("Illegal method: " + this.method);
  }
  
  public String process()
    throws IOException
  {
    try
    {
      int code = this.httpClient.executeMethod(this.httpMethod);
      String str;
      if (code != 200) {
        return "{}";
      }
      return this.httpMethod.getResponseBodyAsString();






    }
    finally
    {





      this.httpMethod.releaseConnection();
    }
  }
  
  public void abort() {
    ((SimpleHttpConnectionManager)this.httpClient.getHttpConnectionManager()).shutdown();
  }
}
