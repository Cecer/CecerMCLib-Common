package com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata;

import com.cecer1.projects.mc.cecermclib.common.environment.AbstractEnvironment;
import com.cecer1.projects.mc.cecermclib.common.environment.IClientEnvironment;
import com.cecer1.projects.mc.cecermclib.common.modules.IModule;

import java.util.HashSet;
import java.util.Set;

public class ChatMetadataModule implements IModule {

    @Override
    public boolean isEnvironmentSupported(AbstractEnvironment environment) {
        return environment instanceof IClientEnvironment;
    }

    private Set<IChatProcessor> chatProcessors;

    public ChatMetadataModule() {
        this.chatProcessors = new HashSet<>();
    }

    public void registerProcessor(IChatProcessor processor) {
        this.chatProcessors.add(processor);
    }

    public ProcessedChatMessageEventData process(ChatMessageData data) {
        ProcessedChatMessageEventData.Builder builder = new ProcessedChatMessageEventData.Builder(data);
        for (IChatProcessor processor : this.chatProcessors) {
            processor.process(builder, data);
        }
        ProcessedChatMessageEventData eventData = builder.build();
        ProcessedChatMessageCallback.EVENT.invoker().handle(eventData);
        return eventData;
    }
}