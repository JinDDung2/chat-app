package com.likelion.chatapp.controller;

import com.likelion.chatapp.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    static class WriteMessageRequest {
        private String writer;
        private String content;
    }

    @AllArgsConstructor
    static class WriteMessageResponse {
        private final long id;
    }

    @PostMapping("/writeMessage")
    @ResponseBody
    public ResultData<WriteMessageResponse> writeMessage(@RequestBody WriteMessageRequest request) {
        ChatMessage message = new ChatMessage(request.getWriter(), request.getContent());

        chatMessages.add(message);

        return new ResultData<>("S-1",
                "메세지가 작성되었습니다.",
                new WriteMessageResponse(message.getId()));
    }

    @AllArgsConstructor
    @Getter
    static class MessagesRequest {
        private final Long fromId;
    }

    @Getter
    @AllArgsConstructor
    static class MessagesResponse {
        private List<ChatMessage> chatMessages;
        private int size;
    }

    @GetMapping("/messages")
    @ResponseBody
    public ResultData<MessagesResponse> messages(MessagesRequest request) {

        // 번호가 입력될 때
        if (request.getFromId() != null) {
            // 해당 번호의 채팅메세지가 전체 리스트에서의 배열인덱스 번호를 구하기
            // 없다면 -1
            int index = IntStream.range(0, chatMessages.size())
                    .filter(i -> chatMessages.get(i).getId() == request.getFromId())
                    .findFirst()
                    .orElse(-1);

            if (index != -1) {
                // 만약에 index 가 있다면, 0번 부터 index 번 까지 제거한 리스트를 만든다.
                chatMessages = chatMessages.subList(index + 1, chatMessages.size());
            }
        }
        return new ResultData<>("S-1",
                "성공",
                new MessagesResponse(chatMessages, chatMessages.size()));
    }

}
