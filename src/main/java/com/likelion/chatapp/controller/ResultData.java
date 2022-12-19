package com.likelion.chatapp.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResultData<T> {

    private String resultCode;
    private String message;
    private T data;

    public ResultData(String resultCode, String message, T data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }
}
