/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.ourchat.chat;

/**
 *
 * @author dark
 */
public enum ReceiverEnum {
    single, group, unknown;

    @Override
    public String toString() {
        return name();
    }

    /**
     *
     * @param value
     * @return enum with String value of param value or unknown enum
     */
    public static ReceiverEnum toEnum(String value) {
        if (null == value) {
            return ReceiverEnum.unknown;
        }
        for (ReceiverEnum receiverEnum : ReceiverEnum.values()) {
            if (receiverEnum.toString().equals(value)) {
                return receiverEnum;
            }
        }
        return ReceiverEnum.unknown;
    }
}
