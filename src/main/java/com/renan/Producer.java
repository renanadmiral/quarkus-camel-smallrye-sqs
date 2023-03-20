package com.renan;

import com.fasterxml.jackson.databind.JsonNode;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Producer {

    @Inject
    SqsClient sqsClient;

    @ConfigProperty(name = "sqs-host")
    String sqsHost;

    @ConfigProperty(name = "queue")
    String userQueue;

    public void produce(final JsonNode payload) {
        sqsClient.sendMessage(SendMessageRequest.builder().queueUrl(sqsHost + userQueue).messageBody(payload.toString()).build());
    }
}
