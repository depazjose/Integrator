package com.example.integration.config;

import com.azure.spring.integration.core.api.CheckpointConfig;
import com.azure.spring.integration.core.api.CheckpointMode;
import com.azure.spring.integration.servicebus.factory.ServiceBusConnectionStringProvider;
import com.azure.spring.integration.servicebus.inbound.ServiceBusTopicInboundChannelAdapter;
import com.azure.spring.integration.servicebus.topic.ServiceBusTopicOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ServiceBusConfig {

  private static final String INPUT_CHANNEL = "topic.product";
  private static final String TOPIC_NAME = "topic";
  private static final String SUBSCRIPTION_NAME = "subs";
  private static final String CS_PRODUCT = "endPoint";

  @Bean (name = CS_PRODUCT)
  public @Qualifier(CS_PRODUCT) ServiceBusConnectionStringProvider serviceBusConnectionStringProviderProduct() {
    return new ServiceBusConnectionStringProvider(CS_PRODUCT);
  }

  @Bean
  public ServiceBusTopicInboundChannelAdapter topicMessageChannelAdapter(
      @Qualifier(INPUT_CHANNEL) MessageChannel inputChannel, ServiceBusTopicOperation topicOperation
      ) {

    topicOperation.setCheckpointConfig(CheckpointConfig.builder().checkpointMode(CheckpointMode.MANUAL).build());
    ServiceBusTopicInboundChannelAdapter adapter = new ServiceBusTopicInboundChannelAdapter(TOPIC_NAME,
        topicOperation, SUBSCRIPTION_NAME);

    adapter.setOutputChannel(inputChannel);

    return adapter;
  }

  @Bean(name = INPUT_CHANNEL)
  public MessageChannel input() {
    return new DirectChannel();
  }
}
