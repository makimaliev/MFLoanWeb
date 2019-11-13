package kg.gov.mf.loan.web.controller.chat;

import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.task.model.Chat;
import kg.gov.mf.loan.task.repository.ChatRepository;
import kg.gov.mf.loan.task.service.ChatService;
import kg.gov.mf.loan.task.service.ChatUserService;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    SystemFileService systemFileService;

    private UserService userService;
    private ChatService chatService;
    private ChatUserService chatUserService;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @Autowired
    public ChatController(UserService userService,
                          ChatService chatService,
                          ChatUserService chatUserService)
    {
        this.userService = userService;
        this.chatService = chatService;
        this.chatUserService = chatUserService;
    }


    @GetMapping("/chat/window/{username}")
    public String chatWindow(@PathVariable("username") String username, Model model){

        if(username.equals("null")){
            model.addAttribute("chosenUser",null);
        }
        else{
            model.addAttribute("chosenUser",username);
        }

        return "/admin/sys/chat";
    }

    @MessageMapping("/chat.sendMessageTo")
    @SendToUser("/queue/public")
    public Chat sendMessageTo(@Payload Chat chatMessage, Principal principal) throws Exception
    {
        chatMessage.setSenderfullname(userService.findByUsername(chatMessage.getSender()).getStaff().getName());
        chatMessage.setReceiverfullname(userService.findByUsername(chatMessage.getReceiver()).getStaff().getName());
        return chatMessage;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Chat sendMessage(@Payload Chat chatMessage, Principal principal)
    {
        Object o = principal;
        chatMessage.setSenderfullname( userService.findByUsername(chatMessage.getSender()).getStaff().getName());
        chatMessage.setReceiverfullname(userService.findByUsername(chatMessage.getReceiver()).getStaff().getName());
        chatRepository.save(chatMessage);
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

    @PostMapping("/attachFile")
    @ResponseBody
    public String saveAttachmentOfChat(@RequestParam("file") MultipartFile file){

        String path =  SystemUtils.IS_OS_LINUX ? "/opt/uploads/" : "C:/temp/";

        SystemFile systemFile=new SystemFile();

        path = path + "chats/";
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }
        String filename = file.getOriginalFilename();

        folder=new File(path+filename);
        if(folder.exists()){
        }
        else{
            File file1 = new File(path + filename);

            try {
                file.transferTo(file1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        systemFile.setName(file.getOriginalFilename());
        systemFile.setPath(path + filename);
        systemFileService.create(systemFile);

        return filename+"^"+systemFile.getId();
    }
}
