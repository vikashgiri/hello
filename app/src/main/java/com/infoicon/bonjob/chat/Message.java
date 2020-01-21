package com.infoicon.bonjob.chat;

import java.util.Comparator;

/**
 * Created by info on 25/6/18.
 */

public class Message  implements Comparable<Message>{
    String fromId="";
    String text="";
    long timeStamp;
    String onlineStatus="";
    String toId="";
    String readStatus="0";
    setUserType from=new setUserType();
    setUserType to=new setUserType();
    String fcmToken="";
    String messageID="";
    String imageUrl="";
    String fileUrl="";
    String videoUrl="";
    String totalUnreadCount="1";
    String deviceType="Android";
    String jobImage="";
    String  msgType="";
    String  fileName="";

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTotalUnreadCount() {
        return totalUnreadCount;
    }

    public void setTotalUnreadCount(String totalUnreadCount) {
        this.totalUnreadCount = totalUnreadCount;
    }

    public String getJobImage() {
        return jobImage;
    }

    public void setJobImage(String jobImage) {
        this.jobImage = jobImage;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }


    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }


    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public setUserType getFrom() {
        return from;
    }

    public void setFrom(setUserType from) {
        this.from = from;
    }

    public setUserType getTo() {
        return to;
    }

    public void setTo(setUserType to) {
        this.to = to;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public static class setUserType {
        String name="";
        String type="";
        String profilePic="";

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }




    @Override
    public int compareTo(Message u) {

        return Long.valueOf(u.timeStamp).compareTo(u.timeStamp);
    }

}