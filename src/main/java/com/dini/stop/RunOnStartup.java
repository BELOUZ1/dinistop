package com.dini.stop;

import com.dini.stop.configuration.DiniStopConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RunOnStartup implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(RunOnStartup.class);
    private DiniStopConfig diniStopConfig;

    @Autowired
    public RunOnStartup(DiniStopConfig diniStopConfig) {
        this.diniStopConfig = diniStopConfig;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOG.debug("DiniStopConfig : {}", diniStopConfig);
    }
}
