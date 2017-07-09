package com.oak.weixin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oak.weixin.entity.AccessTokenResponse;
import com.oak.weixin.entity.WeinxinMessageResponse;
import com.oak.weixin.entity.WeixinMessage;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.apache.http.client.utils.URLEncodedUtils.CONTENT_TYPE;

/**
 * Created by Chennl on 7/7/2017.
 */
@Service
public class WorkWeiXinService {
    private final static Logger logger = LoggerFactory.getLogger(WorkWeiXinService.class);
    private final static String access_token_url="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=id&corpsecret=secrect";
    private final static String send_message_url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";

    @Autowired
    RestTemplate restTemplate;


    // 企业号ID
    public static String corpId = "=wx5411bdb3f186691d";
    // 管理组的凭证密钥，每个secret代表了对应用、通讯录、接口的不同权限；不同的管理组拥有不同的secret
    public static String corpsecret = "=CIIx5rjewI5Iqd5WKP22PNqOO1sHXrR2myDrbrI4CbA";
    // 应用ID，AgentId
    public static int AgentId = 1000003;

    //
    AccessTokenResponse accessTokenObject=null;




   public String createMessage(String touser, String msgtype,
             int application_id, String contentKey ,String contentValue) throws JsonProcessingException
    {
        WeixinMessage msg = new WeixinMessage();
        msg.setTouser(touser);
        msg.setAgentid(application_id);
        msg.setMsgtype(msgtype);
        Map<Object, Object> content = new HashMap<Object, Object>();
        content.put(contentKey,contentValue);
        msg.setText(content);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(msg);
       return json;
    }

    public void sendTextMessage(String touser,String msg){
       try {
           String data = createMessage(touser, "text", AgentId, "content", msg);
           sendMessage(data,getAccessToken());
       }catch (Exception e){
           logger.error(e.getMessage());
       }

    }
    private void sendMessage(String data,String access_token) {
        String url = send_message_url.replace("=ACCESS_TOKEN","="+access_token);
        String charset = "utf-8";
        String contentType ="";
        String response = httpsPostRequest(charset,contentType,url,data);
        if (null != response && response!="") {
            try {
                ObjectMapper mapper = new ObjectMapper();
                WeinxinMessageResponse wxResponse = mapper.readValue(response,WeinxinMessageResponse.class);

                if (wxResponse.getErrcode() == 0) {
                    logger.debug("消息发送成功{}！",data);
                } else {
                    logger.error("消息发送失败 errcode:{}, errmsg:{}, invaliduser:{},invalidparty:{},invalidtag:{}",
                            wxResponse.getErrcode(),
                            wxResponse.getErrmsg(),
                            wxResponse.getInvaliduser(),
                            wxResponse.getInvalidparty(),
                            wxResponse.getInvalidtag()
                    );
                }
            } catch (Exception e) {
                logger.error("消息发送失败");
            }
        }
    }

    public String getAccessToken(){

        String access_token = "";

        if(accessTokenObject !=null){
            if (accessTokenObject.getAcesstoken()==null ||accessTokenObject.getAcesstoken() ==""){
                logger.debug(" cached access-token: ", access_token);
                return accessTokenObject.getAcesstoken();
            }
        }

        String requestUrl = access_token_url.replace("=id",corpId).replace("=secrect",corpsecret);
        String response = httpsGetRequest(requestUrl);

        if (null != response && response !="") {
            try{
                JSONObject jsonObject =new JSONObject(response);
                accessTokenObject = new AccessTokenResponse();
                accessTokenObject.setErrcode(jsonObject.getInt("errcode"));
                accessTokenObject.setErrmsg(jsonObject.getString("errmsg"));
                accessTokenObject.setAcesstoken(jsonObject.getString("access_token"));
                accessTokenObject.setExpiresin(jsonObject.getInt("expires_in"));
                if(accessTokenObject.getErrcode() == 0){
                    access_token = accessTokenObject.getAcesstoken();
                    logger.debug("get access-token: ",access_token);
                }
                else
                {
                    access_token =null;
                    logger.error("获取token失败 errcode:{} errmsg:{}",
                            accessTokenObject.getErrcode(),
                            accessTokenObject.getErrmsg());
                }
            }catch (JSONException je){
                logger.error(je.toString());
                access_token =null;
            }

        }
        return access_token;
    }

    public static String httpsGetRequest(String requestUrl) {

         String urlOverHttps = requestUrl;
         HttpGet getMethod = new HttpGet(urlOverHttps);
         return httpsRequest(getMethod);

    }
    public static String httpsPostRequest(String charset, String contentType, String url,
                                              String data) {

        JSONObject jsonObject = null;
        String urlOverHttps = url;
        HttpPost httpPost = new HttpPost(urlOverHttps);
        httpPost.setHeader(CONTENT_TYPE, contentType);
        httpPost.setEntity(new StringEntity(data, charset));
        return httpsRequest(httpPost);
    }
    public static String httpsRequest(HttpUriRequest uriRequest) {

        String responseString ="";
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpResponse response = httpClient.execute(uriRequest);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            }else
            {
                responseString =null;
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        }catch (Exception e){
            logger.debug(e.toString());
        }
        return responseString;
    }


}
