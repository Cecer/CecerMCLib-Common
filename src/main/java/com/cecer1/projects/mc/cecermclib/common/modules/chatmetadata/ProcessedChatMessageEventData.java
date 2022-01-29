package com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessedChatMessageEventData {
    private final ChatMessageData messageData;
    public ChatMessageData getMessageData() {
        return this.messageData;
    }

    private List<ChatMessageData> outputOverride;
    public List<ChatMessageData> getOutputOverride() {
        return this.outputOverride;
    }
    public void setOutputOverride(List<ChatMessageData> outputOverride) {
        this.outputOverride = outputOverride;
    }

    private final Map<Class<? extends IChatMetadata>, IChatMetadata> metadata;

    public ProcessedChatMessageEventData(ChatMessageData messageData, Map<Class<? extends IChatMetadata>, IChatMetadata> metadata) {
        this.messageData = messageData;
        this.metadata = Collections.unmodifiableMap(metadata);
    }

    public <T extends IChatMetadata> T getMetadata(Class<T> clazz) {
        return clazz.cast(this.metadata.get(clazz));
    }


    public static class Builder {
        private final ChatMessageData messageData;
        private final Map<Class<? extends IChatMetadata>, IChatMetadata> metadata;

        public Builder(ChatMessageData messageData) {
            this.messageData = messageData;
            this.metadata = new HashMap<>();
        }

        public Builder addMetadata(IChatMetadata metadata) {
            this.metadata.put(metadata.getClass(), metadata);
            return this;
        }

        public ProcessedChatMessageEventData build() {
            return new ProcessedChatMessageEventData(messageData, metadata);
        }
    }
}
