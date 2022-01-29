package com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata;

import com.cecer1.projects.mc.cecermclib.common.modules.text.WrappedComponent;

public class ChatMessageData {

    private final WrappedComponent<?> component;
    private final int messageName;

    public WrappedComponent<?> getComponent() {
        return this.component;
    }
    /**
     * The message "name" is a non-zero integer used for the purpose of removing the message later.
     * If a message name conflicts with a previous message then only the most recent one will be deleted.
     * A value of 0 means the message has no name and will not conflict with other messages.
     * A value of 1 is used by Minecraft for displaying tab complete results in versions prior to 1.13.
     */
    public int getMessageName() {
        return this.messageName;
    }

    public ChatMessageData(WrappedComponent<?> component, int messageName) {
        this.component = component;
        this.messageName = messageName;
    }
}
