package org.example.mqttserver;


import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class MqttConfig {

    @Bean
    public Mqtt3BlockingClient mqttClient() {
        final String host = "eu1.cloud.thethings.network";
        final int port = 8883;
        final String username = "lora-test-ucl@ttn";
        final String password = "NNSXS.NFKEWOTTQOL4HFFFOUPNSDMRRDWCRBBDBALPY4Y.OWIAPOOBIJXG2VLFDVFXL4RUMDJUU3R24QYK47UEKHRVTUUTIY2Q";

        final Mqtt3BlockingClient client = MqttClient.builder()
                .useMqttVersion3()
                .serverHost(host)
                .serverPort(port)
                .sslWithDefaultConfig()
                .buildBlocking();
        client.connectWith()
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .send();
        return client;
    }
}
