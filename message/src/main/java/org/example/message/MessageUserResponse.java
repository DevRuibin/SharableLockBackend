package org.example.message;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.example.message.client.UserModel;

import java.util.List;

@ToString
@Data
@Builder
public class MessageUserResponse {
    private UserModel user;
    private int unreadMessageNum;
    private List<MessageModel> messages;

    public void increaseUnreadNum(){
        unreadMessageNum++;
    }
}
