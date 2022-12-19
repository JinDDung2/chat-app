package com.likelion.chatapp.controller;

import com.likelion.chatapp.entity.ChatMessage;
import com.likelion.chatapp.sse.SseEmitters;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/chat")
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final SseEmitters sseEmitters;

    private List<ChatMessage> chatMessageList = new ArrayList<>();

    @GetMapping("/room")
    public String room() {
        return "chat/room";
    }


    @Getter
    public static class WriteMessageRequest {
        private String writer;
        private String content;

        public WriteMessageRequest() {
        }

        public WriteMessageRequest(String writer, String content) {
            this.writer = writer;
            this.content = content;
        }
    }

    @AllArgsConstructor
    @Getter
    public static class WriteMessageResponse {
        private final long id;
    }

    @PostMapping("/writeMessage")
    @ResponseBody
    public ResultData<WriteMessageResponse> writeMessage(@RequestBody WriteMessageRequest req) {
        ChatMessage message = new ChatMessage(req.writer, req.content);

        chatMessageList.add(message);

        sseEmitters.noti("chat__messageAdded");

        return new ResultData<>("S-1",
                "메세지가 작성되었습니다.",
                new WriteMessageResponse(message.getId()));
    }

    @AllArgsConstructor
    @Getter
    public static class MessagesRequest {
        private final Long fromId;
    }

    @AllArgsConstructor
    @Getter
    public static class MessagesResponse {
        private final List<ChatMessage> chatMessageList;
        private final int size;
    }

    @GetMapping("/message")
    @ResponseBody
    public ResultData<MessagesResponse> message(MessagesRequest req) {
        log.debug("req : {}",  req);

        List<ChatMessage> messages = chatMessageList;

        if(req.fromId != null) {
            int index = IntStream.range(0, messages.size())
                    .filter(i -> chatMessageList.get(i).getId() == req.fromId)
                    .findFirst()
                    .orElse(-1);

            if(index != -1) {
                messages = messages.subList(index + 1, messages.size());
            }
        }

        return new ResultData<>(
                "S-1",
                "Success",
                new MessagesResponse(messages, messages.size()));
    }

}
