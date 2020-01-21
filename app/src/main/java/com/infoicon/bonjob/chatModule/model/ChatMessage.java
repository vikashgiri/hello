package com.infoicon.bonjob.chatModule.model;

import java.util.Locale;
import java.util.Random;

/**
 * Created by infoicona on 15/6/17.
 */

public class ChatMessage {

    public boolean isMine;
    public String date, time;
    public String content;
    public String sender;
    public String receiver;
    public String msgid;

    public ChatMessage(String sender, String receiver, String message,String msgId, boolean mine, String date, String time) {
        content = message;
        isMine = mine;
        this.date = date;
        this.time = time;
        this.sender = sender;
        this.receiver = receiver;
        this.msgid=msgId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }

    public void setMsgID() {

        msgid += "-" + String.format("%02d", new Random().nextInt(100));
        ;
    }
}