package com.likelion.chatapp.controller;

import com.likelion.chatapp.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @AllArgsConstructor
    @Getter
    public static class WriteMessageRequest {
        private final String writer;
        private final String content;
    }

    @AllArgsConstructor
    public static class WriteMessageResponse {
        private final long id;
    }

    @PostMapping("/writeMessage")
    @ResponseBody
    public ResultData<WriteMessageResponse> writeMessage(@RequestBody WriteMessageRequest request) {
        ChatMessage chatMessage = new ChatMessage(request.getWriter(), request.getContent());

        chatMessages.add(chatMessage);

        return new ResultData<>("S-1",
                "메세지가 작성되었습니다.",
                new WriteMessageResponse(chatMessage.getId()));
    }

    @GetMapping("/messages")
    @ResponseBody
    public ResultData<List<ChatMessage>> messages() {
        return new ResultData<>("S-1",
                "성공",
                chatMessages);
    }


}
