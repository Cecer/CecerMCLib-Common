package com.cecer1.projects.mc.cecermclib.common.modules;

import com.cecer1.projects.mc.cecermclib.common.events.CMLEvent;
import com.cecer1.projects.mc.cecermclib.common.events.CMLEventFactory;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Called when CecerMCLib is ready for modules to register themselves.
 * No modules should be registered outside this event! 
 */
public interface ModuleRegistrationCallback {
    CMLEvent<ModuleRegistrationCallback> EVENT = CMLEventFactory.createArrayBacked(ModuleRegistrationCallback.class,
            listeners -> (RegistrationContext ctx) -> {
                for (ModuleRegistrationCallback listener : listeners) {
                    listener.handle(ctx);
                }
            });

    void handle(RegistrationContext ctx);

    final class RegistrationContext {
        private final ModuleManager moduleManager;

        RegistrationContext(ModuleManager moduleManager) {
            this.moduleManager = moduleManager;
        }

        public <TModule extends IModule> void registerModule(@NonNull TModule module) {
            this.moduleManager.registerModule(module);
        }

        /**
         * Registers a module as a service module.
         */
        public <TService extends IModule, TModule extends TService> void registerServiceModule(Class<TService> serviceClass, TModule module) {
            this.moduleManager.registerServiceModule(serviceClass, module);
        }
    }
}