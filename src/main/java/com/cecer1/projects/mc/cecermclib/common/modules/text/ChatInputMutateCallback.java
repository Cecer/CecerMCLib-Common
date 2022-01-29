package com.cecer1.projects.mc.cecermclib.common.modules.text;

import com.cecer1.projects.mc.cecermclib.common.events.CMLEvent;
import com.cecer1.projects.mc.cecermclib.common.events.CMLEventFactory;

// TODO: This should be moved to another module.

public interface ChatInputMutateCallback {
    CMLEvent<ChatInputMutateCallback> EVENT = CMLEventFactory.createArrayBacked(ChatInputMutateCallback.class,
            listeners -> (value, shouldReplace) -> {
                for (ChatInputMutateCallback listener : listeners) {
                    listener.handle(value, shouldReplace);
                }
            });

    /**
     * @param value The value you place in the chat input
     * @param shouldReplace If true then the value will replace any existing chat input. If false then it will be inserted into existing chat input.
     */
    void handle(String value, boolean shouldReplace);
}
