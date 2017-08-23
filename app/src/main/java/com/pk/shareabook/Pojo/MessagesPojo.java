package com.pk.shareabook.Pojo;

/**
 * Created by Harry on 8/23/2017.
 */

public class MessagesPojo {

      private   String chat_sender_id;
    private String chat_reciever;
    private String message;
    private String chat_request_id;
    private String date;
    private String recieverID;

    public String getRecieverID() {
        return recieverID;
    }

    public void setRecieverID(String recieverID) {
        this.recieverID = recieverID;
    }

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    private String chatid;

    public String getChat_sender_id() {
        return chat_sender_id;
    }

    public void setChat_sender_id(String chat_sender_id) {
        this.chat_sender_id = chat_sender_id;
    }

    public String getChat_reciever() {
        return chat_reciever;
    }

    public void setChat_reciever(String chat_reciever) {
        this.chat_reciever = chat_reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChat_request_id() {
        return chat_request_id;
    }

    public void setChat_request_id(String chat_request_id) {
        this.chat_request_id = chat_request_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


