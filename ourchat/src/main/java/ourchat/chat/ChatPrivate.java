/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ourchat.chat;

/**
 *
 * @author dark
 */
public class ChatPrivate extends ChatBase {

    private long receiverId;

    /**
     * @return the receiverId
     */
    public long getReceiverId() {
        return receiverId;
    }

    /**
     * @param receiverId the receiverId to set
     */
    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

}
