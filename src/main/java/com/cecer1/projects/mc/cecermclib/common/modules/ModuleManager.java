package com.cecer1.projects.mc.cecermclib.common.modules;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("RedundantStringFormatCall") // Because some environments break printf
public final class ModuleManager {

    private final Map<Class<? extends IModule>, IModule> modules;
    private final Map<Class<? extends IModule>, Class<? extends IModule>> serviceModules;
    private volatile boolean registrationStarted;

    public ModuleManager() {
        this.modules = new HashMap<>();
        this.serviceModules = new HashMap<>();
    }

    <TModule extends IModule> ModuleManager registerModule(@NonNull TModule module) {
        if (this.modules.putIfAbsent(module.getClass(), module) != null) {
            throw new UnsupportedOperationException(String.format("Duplicate module registration of type \"%s\"", module.getClass().getName()));
        }

        System.out.println("[CecerMCLib] [ModuleManager] Registered module: " + module.getClass().getName());
        module.onModuleRegister();

        return this;
    }

    /**
     * Registers a module as a service module.
     */
    <TService extends IModule, TModule extends TService> ModuleManager registerServiceModule(Class<TService> serviceClass, TModule module) {
        Class<? extends IModule> moduleClass = module.getClass();

        if (this.modules.putIfAbsent(module.getClass(), module) != null) {
            throw new UnsupportedOperationException(String.format("Duplicate module registration of type \"%s\"", module.getClass().getName()));
        }
        if (this.modules.putIfAbsent(module.getClass(), module) != null) {
            System.err.println(String.format("[CecerMCLib] [ModuleManager] Warning: Keeping previous service module instead of %s for %s", moduleClass.getName(), serviceClass.getName()));
        }
        System.out.println(String.format("[CecerMCLib] [ModuleManager] Registered service module: %s -> %s", moduleClass.getName(), serviceClass.getName()));
        return this;
    }

    public <TModule extends IModule> TModule get(@NonNull Class<TModule> moduleClass) {
        TModule module = moduleClass.cast(this.modules.get(moduleClass));
        if (module == null) {
            //noinspection unchecked
            Class<TModule> aliasClass = (Class<TModule>) this.serviceModules.get(moduleClass);
            if (aliasClass != null) {
                return this.get(aliasClass);
            }
        }
        return module;
    }
    public <TModule extends IModule> TModule getService(@NonNull Class<TModule> moduleClass) {
        //noinspection unchecked
        Class<TModule> aliasClass = (Class<TModule>) this.serviceModules.get(moduleClass);
        if (aliasClass != null) {
            return this.get(aliasClass);
        }
        return null;
    }
    
    public void registerModules() {
        if (this.registrationStarted) {
            throw new IllegalStateException("Multiple call to ModuleManager#completeRegistration");
        }
        this.registrationStarted = true;

        System.out.println("[CecerMCLib] [ModuleManager] Registering modules...");
        ModuleRegistrationCallback.EVENT.invoker().handle(new ModuleRegistrationCallback.RegistrationContext(this));
        System.out.println("[CecerMCLib] [ModuleManager] Modules registered: " + this.modules.size());
        
        System.out.println("[CecerMCLib] [ModuleManager] Checking module dependencies...");
        boolean failed = false;
        for (IModule module : this.modules.values()) {
            for (Class<? extends IModule> dependency : module.getDependencies()) {
                if (!this.modules.containsKey(dependency)) {
                    System.err.println(String.format("[CecerMCLib] [ModuleManager] Missing module dependency \"%s\" for \"%s\"!", dependency.getName(), module.getClass()));
                    failed = true;
                }
            }
        }
        if (failed) {
            throw new IllegalStateException("Missing dependencies!");
        }
        System.out.println("[CecerMCLib] [ModuleManager] Module dependencies met!");

        System.out.println("[CecerMCLib] [ModuleManager] Invoking AllModulesRegisteredCallback...");
        AllModulesRegisteredCallback.EVENT.invoker().handle();
        
        System.out.println("[CecerMCLib] [ModuleManager] Invoking AllModulesInitialisedCallback...");
        AllModulesInitialisedCallback.EVENT.invoker().handle();
        
        System.out.println("[CecerMCLib] [ModuleManager] Module registration complete!");
    }
}
