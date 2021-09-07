package com.project.localbatter.entity.Exchange;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.PostUpdate;

public class WriterClientJoinListenerEntity {

    private final Logger log = LogManager.getLogger();

    @PostUpdate
    public void postUpdate(WriterClientJoinEntity writerClientJoinEntity) {

        log.info("post");

    }
}
