package com.dini.stop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RunOnStartup implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(RunOnStartup.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOG.info("Startup sequence: success !!!");
    }
}
