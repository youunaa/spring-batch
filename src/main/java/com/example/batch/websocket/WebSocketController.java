package com.example.batch.websocket;

import com.example.batch.websocket.model.Socket;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    /**
     *
     * @param socketVO
     * @return
     */
    @SendTo("/send") // /send로 메시지를 반환합니다.
    @MessageMapping("/receive")
    public Socket SocketHandler(Socket socketVO) {
        String userName = socketVO.getUserName();
        String content = socketVO.getContent();

        // 생성자로 반환값을 생성합니다.
        Socket result = new Socket(userName, content);
        // 반환
        return result;
    }

    //receive를 메시지를 받을 endpoint로 설정합니다.
    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
    // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용합니다.

}
