/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.ourchat.chat;

import app.core.domain.Model;

/**
 * Chat base superclass
 *
 * @author dark
 */
public class ChatBase extends Model {

    private long senderId;
//    private long receiverId; // can be single profile or group
//    private ReceiverEnum receiver; // type of chat, private (single) or group
    private long messageId;

    /**
     * @return the senderId
     */
    public long getSenderId() {
        return senderId;
    }

    /**
     * @param senderId the senderId to set
     */
    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

//    /**
//     * @return the receiverId
//     */
//    public long getReceiverId() {
//        return receiverId;
//    }
//
//    /**
//     * @param receiverId the receiverId to set
//     */
//    public void setReceiverId(long receiverId) {
//        this.receiverId = receiverId;
//    }
//
//    /**
//     * @return the receiver
//     */
//    public ReceiverEnum getReceiver() {
//        return receiver;
//    }
//
//    /**
//     * @param receiver the receiver to set
//     */
//    public void setReceiver(ReceiverEnum receiver) {
//        this.receiver = receiver;
//    }
    /**
     * @return the messageId
     */
    public long getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

}
