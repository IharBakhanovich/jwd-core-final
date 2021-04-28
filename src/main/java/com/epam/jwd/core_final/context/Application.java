package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.ApplicationMenuImpl;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.util.function.Supplier;

public interface Application {

    static ApplicationMenu start() throws InvalidStateException {
        final Supplier<ApplicationContext> applicationContextSupplier = null; // todo
        final NassaContext nassaContext = new NassaContext();
        nassaContext.init();
        ApplicationMenuImpl.INSTANCE.handleUserInput(nassaContext);
        //return applicationContextSupplier::get;
        return null; //(ApplicationMenu) ApplicationMenuImpl.INSTANCE.handleUserInput(nassaContext);
    }
}
