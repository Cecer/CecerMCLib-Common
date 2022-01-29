package com.cecer1.projects.mc.cecermclib.common.modules;

import com.cecer1.projects.mc.cecermclib.common.events.CMLEvent;
import com.cecer1.projects.mc.cecermclib.common.events.CMLEventFactory;

/**
 * Called after all modules have been registered.
 * This is a good place to do any initialisation that depends on other modules being registered.
 */
public interface AllModulesRegisteredCallback {
    CMLEvent<AllModulesRegisteredCallback> EVENT = CMLEventFactory.createArrayBacked(AllModulesRegisteredCallback.class,
            listeners -> () -> {
                for (AllModulesRegisteredCallback listener : listeners) {
                    listener.handle();
                }
            });

    void handle();
}