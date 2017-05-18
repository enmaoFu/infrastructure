package com.infrastructure.websocket;

import org.apache.commons.collections4.MapUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

public class TextMessageHandler extends TextWebSocketHandler {

    private static List<Obj> list = new ArrayList<>();
    private static final Map<String, WebSocketSession> users;

    static {
        users = new HashMap<String, WebSocketSession>();
    }

    /*
     * 链接成功后会触发此方法，可在此处对离线消息什么的进行处理
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //当前用户ID
        String userId = MapUtils.getString(session.getAttributes(), "userId");
        //当前群组ID
        String groupId = MapUtils.getString(session.getAttributes(), userId);
        Obj obj = new Obj();
        obj.setGroupId(groupId);
        obj.setUserId(userId);
        obj.setSession(session);
        list.add(obj);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        /*
         * 前端 websocket.send() 会触发此方法 
         */
        System.out.println("message -> " + message.getPayload());
        super.handleTextMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        //当前用户ID
        String userId = MapUtils.getString(session.getAttributes(), "userId");
        //当前群组ID
        String groupId = MapUtils.getString(session.getAttributes(), userId);
        for (int i = 0; i < list.size(); i++) {
            Obj obj = list.get(i);
            if(obj.getGroupId().equals(groupId) && obj.getUserId().equals(userId)){
                list.remove(i);
                break;
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //当前用户ID
        String userId = MapUtils.getString(session.getAttributes(), "userId");
        //当前群组ID
        String groupId = MapUtils.getString(session.getAttributes(), userId);
        for (int i = 0; i < list.size(); i++) {
            Obj obj = list.get(i);
            if(obj.getGroupId().equals(groupId) && obj.getUserId().equals(userId)){
                list.remove(i);
                break;
            }
        }
    }

    /**
     * 向某个人发送消息
     * @param userId
     * @param message
     */
    public void sendMessageToUser(String userId, TextMessage message) {
        Iterator<Map.Entry<String, WebSocketSession>> it = userIterator();
        while (it.hasNext()) {
            WebSocketSession session = it.next().getValue();
            if (userId.equals(session.getAttributes().get("userId"))) {
                try {
                    if (session.isOpen())
                        session.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 向所有人发送消息
     * @param message
     */
    public void sendMessageToUsers(String groupId,TextMessage message){
        try {
            for (Obj obj : list) {
                if(obj.getGroupId().equals(groupId)) {
                    obj.getSession().sendMessage(message);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Iterator<Map.Entry<String, WebSocketSession>> userIterator() {
        Set<Map.Entry<String, WebSocketSession>> entrys = users.entrySet();
        return entrys.iterator();
    }

    class Obj{
        private String groupId;
        private String userId;
        private WebSocketSession session;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public WebSocketSession getSession() {
            return session;
        }

        public void setSession(WebSocketSession session) {
            this.session = session;
        }
    }
}
