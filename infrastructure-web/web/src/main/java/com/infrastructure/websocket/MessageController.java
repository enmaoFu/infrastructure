package com.infrastructure.websocket;

import com.infrastructure.entity.culture.GroupsUser;
import com.infrastructure.service.culture.IGroupService;
import com.infrastructure.entity.system.SysUser;
import com.infrastructure.spring.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private IGroupService iGroupService;

    @Bean
    public TextMessageHandler textMessageHandler() {
        return new TextMessageHandler();
    }

    @RequestMapping
    public String view() {
        return "message";
    }

    /**
     * 发送消息
     * @param user
     * @param groupId
     * @param message
     * @return
     */
    @RequestMapping("/send")
    @ResponseBody
    public String send(@CurrentUser SysUser user,String groupId, String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        message = message.replace("\n","/n/");
        TextMessage msg = new TextMessage("{'name':'"+user.getUsername()+"','time':'"+sdf.format(Calendar.getInstance().getTime())+"','message':'"+message+"'}");
        textMessageHandler().sendMessageToUsers(groupId,msg);
        GroupsUser groupsUser = iGroupService.findGroupIdAndUserId(groupId, user.getId());
        iGroupService.insertMessages(groupsUser.getId(),message);
        return "true";
    }

}
