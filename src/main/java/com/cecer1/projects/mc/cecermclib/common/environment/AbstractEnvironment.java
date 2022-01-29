package com.cecer1.projects.mc.cecermclib.common.environment;

import com.cecer1.projects.mc.cecermclib.common.CecerMCLib;
import com.cecer1.projects.mc.cecermclib.common.config.ICecerMCLibConfig;
import com.cecer1.projects.mc.cecermclib.common.modules.ModuleRegistrationCallback;
import com.cecer1.projects.mc.cecermclib.common.modules.logger.LoggerModule;

public abstract class AbstractEnvironment {

    private final Side side;
    public Side getSide() {
        return this.side;
    }
    
    public abstract ICecerMCLibConfig getConfig();

    protected AbstractEnvironment(Side side) {
        this.side = side;
    }
    
    public void registerModules(ModuleRegistrationCallback.RegistrationContext ctx) {
        ctx.registerModule(new LoggerModule<>(LoggerModule.Channel::new));
        CecerMCLib.get(LoggerModule.class).getChannel(this.getClass()).log("Logger registered!");
        
//        ctx.registerModule(new TextModule(this.createTextAdapter()))
//        ctx.registerModule(new TreeMenuModule())
//        ctx.registerModule(new TabCompleteModule());
    }

    public enum Side {
        CLIENT,
        SERVER
    }
}
