package org.example.sweeter.controller;
import org.example.sweeter.domain.Message;
import org.example.sweeter.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name,
                           Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }
    @GetMapping("/main")
    public String main(Map<String, Object> model){
        Iterable<Message>messages=messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }
    @PostMapping("/main")
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model){
        Message message = new Message(text, tag);
        messageRepo.save(message);
        Iterable<Message>messages=messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }
    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()){
        messages = messageRepo.findByTag(filter);
        }
        else {
            messages=messageRepo.findAll();
        }
        model.put("messages",messages);
        return "main";
    }

}