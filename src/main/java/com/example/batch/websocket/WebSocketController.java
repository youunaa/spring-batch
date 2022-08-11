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
    @SendTo("/send")
    @MessageMapping("/receive")
    public Socket SocketHandler(Socket socketVO) {
        String userName = socketVO.getUserName();
        String content = socketVO.getContent();

        Socket result = new Socket(userName, content);
        return result;
    }

}
