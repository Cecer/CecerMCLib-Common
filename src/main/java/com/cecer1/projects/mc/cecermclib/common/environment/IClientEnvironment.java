package com.cecer1.projects.mc.cecermclib.common.environment;

import com.cecer1.projects.mc.cecermclib.common.modules.ModuleRegistrationCallback;
import com.cecer1.projects.mc.cecermclib.common.modules.chatmetadata.ChatMetadataModule;

public interface IClientEnvironment extends ISidedEnvironment {

    default void registerSideModules(ModuleRegistrationCallback.RegistrationContext ctx) {
        ctx.registerModule(new ChatMetadataModule());
//        ctx.registerModule(new ContextMenuModule());

//        CecerMCLib.get(TabCompleteModule.class)
//                .registerProvider(new RemoteServerCommandResultProvider());
    }
}
