package com.test.test_app.network;

import lombok.Data;

@Data
public class BaseResponse<T> {
    //200 - 성공
    private String responseCode = null;

    //성공시 빈 값, 실패시 메세지 값 기입
    private String responseMsg = null;

    private T data = null;
}
