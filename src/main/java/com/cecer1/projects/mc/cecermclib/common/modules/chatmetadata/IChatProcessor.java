package com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata;

@FunctionalInterface
public interface IChatProcessor {
    void process(ProcessedChatMessageEventData.Builder builder, ChatMessageData data);
}
