package com.likelion.chatapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    private String writer;
    private String content;

    public ChatMessage(String writer, String content) {
        this(ChatMessageGenerator.getNextId(), LocalDateTime.now(), writer, content);
    }

    static class ChatMessageGenerator {
        private static long id = 0;

        public static long getNextId() {
            return ++id;
        }
    }
}
