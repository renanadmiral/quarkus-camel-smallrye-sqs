package com.renan;

import com.fasterxml.jackson.databind.JsonNode;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Producer {

    @Inject
    SqsClient sqsClient;

    public void produce(final JsonNode payload) {
        sqsClient.sendMessage(SendMessageRequest.builder().queueUrl("http://localhost:4566/000000000000/user-queue").messageBody(payload.toString()).build());
    }
}
