package com.cecer1.projects.mc.cecermclib.common.modules;

import com.cecer1.projects.mc.cecermclib.common.environment.AbstractEnvironment;

import java.util.Collections;
import java.util.Set;

public interface IModule {

    default boolean isEnvironmentSupported(AbstractEnvironment environment) {
        return true;
    }

    default Set<Class<? extends IModule>> getDependencies() {
        return Collections.emptySet();
    }

    /**
     * Called as soon as the module is registered.
     * Any module dependencies are NOT guaranteed to be registered at this point!
     *
     * @see AllModulesRegisteredCallback
     * @see AllModulesInitialisedCallback
     */
    default void onModuleRegister() {}
}
