package kg.gov.mf.loan.web.controller.chat;

import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.task.model.Chat;
import kg.gov.mf.loan.task.service.ChatService;
import kg.gov.mf.loan.task.service.ChatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {

    private UserService userService;
    private ChatService chatService;
    private ChatUserService chatUserService;

    @Autowired
    public ChatController(UserService userService,
                          ChatService chatService,
                          ChatUserService chatUserService)
    {
        this.userService = userService;
        this.chatService = chatService;
        this.chatUserService = chatUserService;
    }

    @MessageMapping("/chat.sendMessageTo")
    @SendToUser("/queue/public")
    public Chat sendMessageTo(@Payload Chat chatMessage, Principal principal) throws Exception
    {
        /*
        chatMessage.setSenderfullname(userService.findByUsername(chatMessage.getSender()).getStaff().getName());
        chatMessage.setReceiverfullname(userService.findByUsername(chatMessage.getReceiver()).getStaff().getName());
        */
        return chatMessage;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Chat sendMessage(@Payload Chat chatMessage, Principal principal)
    {
        Object o = principal;
        /*
        chatMessage.setSenderfullname( userService.findByUsername(chatMessage.getSender()).getStaff().getName());
        chatMessage.setReceiverfullname(userService.findByUsername(chatMessage.getReceiver()).getStaff().getName());
        */
        chatService.add(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Chat addUser(@Payload Chat chatMessage, SimpMessageHeaderAccessor headerAccessor)
    {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @RequestMapping("/chat/messages")
    @ResponseBody
    public List<Chat> getMessages(@RequestParam String sender, @RequestParam String receiver)
    {
        return chatService.getMessages(sender, receiver);
    }
}
