package com.cecer1.projects.mc.cecermclib.common;

import com.cecer1.projects.mc.cecermclib.common.environment.AbstractEnvironment;
import com.cecer1.projects.mc.cecermclib.common.modules.IModule;
import com.cecer1.projects.mc.cecermclib.common.modules.ModuleManager;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CecerMCLib {

    private static AbstractEnvironment environment;
    public static AbstractEnvironment getEnvironment() {
        return environment;
    }
    
    private static CecerMCLib instance;
    public static CecerMCLib get() {
        if (CecerMCLib.instance == null) {
            throw new IllegalStateException("CecerMCLib has not been initialised with an environment yet!");
        }
        return CecerMCLib.instance;
    }

    public static CecerMCLib initEnvironment(@NonNull AbstractEnvironment environment) {
        CecerMCLib.environment = environment;
        CecerMCLib.instance = new CecerMCLib();
        CecerMCLib.instance.moduleManager.registerModules();

        return CecerMCLib.instance;
    }

    public static <TModule extends IModule> TModule get(@NonNull Class<TModule> moduleClass) {
        return CecerMCLib.get().getModuleManager().get(moduleClass);
    }
    
    private CecerMCLib() {
        this.moduleManager = new ModuleManager();
    }

    private ModuleManager moduleManager;
    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

}
