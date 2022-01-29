package com.cecer1.projects.mc.cecermclib.common.modules;

import com.cecer1.projects.mc.cecermclib.common.events.CMLEvent;
import com.cecer1.projects.mc.cecermclib.common.events.CMLEventFactory;

/**
 * Called after all modules have been registered and initialised.
 * This may a good place to register schedulers and certain event handlers.
 * 
 * All modules are considered safe to interact with at this point.
 * 
 * This is called after {@link AllModulesRegisteredCallback}. 
 */
public interface AllModulesInitialisedCallback {
    CMLEvent<AllModulesInitialisedCallback> EVENT = CMLEventFactory.createArrayBacked(AllModulesInitialisedCallback.class,
            listeners -> () -> {
                for (AllModulesInitialisedCallback listener : listeners) {
                    listener.handle();
                }
            });

    void handle();
}