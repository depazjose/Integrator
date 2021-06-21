package com.example.integration.component;

import com.azure.spring.integration.core.AzureHeaders;
import com.azure.spring.integration.core.api.Checkpointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class ProductReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductReceiver.class);
  private static final String INPUT_CHANNEL = "topic.product";

  @ServiceActivator(inputChannel = INPUT_CHANNEL)
  public void messageReceiver(byte[] payload, @Header(AzureHeaders.CHECKPOINTER) Checkpointer checkpointer) {
    String message = new String(payload);
    LOGGER.info("New message received: '{}'", message);
    checkpointer.success().handle((r, ex) -> {
      if (ex == null) {
        LOGGER.info("Message '{}' successfully checkpointed", message);
      }
      return null;
    });
  }
}
