package com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata;

import com.cecer1.projects.mc.cecermclib.common.events.CMLEvent;
import com.cecer1.projects.mc.cecermclib.common.events.CMLEventFactory;

public interface ProcessedChatMessageCallback {
    CMLEvent<ProcessedChatMessageCallback> EVENT = CMLEventFactory.createArrayBacked(ProcessedChatMessageCallback.class,
            listeners -> eventData -> {
                for (ProcessedChatMessageCallback listener : listeners) {
                    listener.handle(eventData);
                }
            });

    void handle(ProcessedChatMessageEventData eventData);
}