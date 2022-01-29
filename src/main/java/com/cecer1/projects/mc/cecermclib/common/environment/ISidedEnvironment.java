package com.cecer1.projects.mc.cecermclib.common.environment;

import com.cecer1.projects.mc.cecermclib.common.modules.ModuleRegistrationCallback;

public interface ISidedEnvironment {

    default void registerSideModules(ModuleRegistrationCallback.RegistrationContext ctx) {}
}
