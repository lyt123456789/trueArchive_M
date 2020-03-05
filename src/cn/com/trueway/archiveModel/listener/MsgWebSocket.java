package cn.com.trueway.archiveModel.listener;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import com.google.gson.JsonObject;
 
import net.sf.json.JSONObject;
@ServerEndpoint("/msgWebSocket/{username}")  
public class MsgWebSocket { 
    private static int onlineCount = 0; 
    private static Map<String, MsgWebSocket> clients = new ConcurrentHashMap<String, MsgWebSocket>(); 
    private Session session; 
    private String username; 
       
    @OnOpen 
    public void onOpen(@PathParam("username") String username, Session session) throws IOException { 
   
        this.username = username; 
        this.session = session; 
           
        addOnlineCount(); 
        clients.put(username, this);
        System.out.println("已连接");
    } 
   
    @OnClose 
    public void onClose() throws IOException { 
        clients.remove(username); 
        subOnlineCount(); 
    } 
   
    @OnMessage 
    public static void onMessage(String message) throws IOException { 
    	//msgType   1：右下角消息框  2：申请中心 。。。。
        JSONObject jsonTo = JSONObject.fromObject(message); 
        //String mes = (String) jsonTo.get("message");
        if (!jsonTo.get("To").equals("All")){ 
            sendMessageTo(message, jsonTo.get("To").toString()); 
        }else{ 
            sendMessageAll(message); 
        } 
    } 
   
    @OnError 
    public void onError(Session session, Throwable error) { 
        error.printStackTrace(); 
    } 
   
    public static void sendMessageTo(String message, String To) throws IOException { 
        // session.getBasicRemote().sendText(message); 
        //session.getAsyncRemote().sendText(message); 
        for (MsgWebSocket item : clients.values()) { 
            if (item.username.equals(To) ) 
                item.session.getAsyncRemote().sendText(message); 
        } 
    } 
       
    public static void sendMessageAll(String message) throws IOException { 
        for (MsgWebSocket item : clients.values()) { 
            item.session.getAsyncRemote().sendText(message); 
        } 
    } 
   
    public static synchronized int getOnlineCount() { 
        return onlineCount; 
    } 
   
    public static synchronized void addOnlineCount() { 
        MsgWebSocket.onlineCount++; 
    } 
   
    public static synchronized void subOnlineCount() { 
        MsgWebSocket.onlineCount--; 
    } 
   
    public static synchronized Map<String, MsgWebSocket> getClients() { 
        return clients; 
    } 
}
