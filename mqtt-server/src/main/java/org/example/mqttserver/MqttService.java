package org.example.mqttserver;

import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class MqttService {

    private final Mqtt3BlockingClient client;

    public MqttService(Mqtt3BlockingClient client) {
        this.client = client;
    }

    @PostConstruct
    public void subscribe() {
        System.out.println("Subscribe function called");
        client.subscribeWith()
                .topicFilter("v3/lora-test-ucl@ttn/devices/eui-70b3d57ed00626b8/up")
                .send();

        new Thread(() -> {
            while (true) {
                try {
                    Mqtt3Publish publish = client.publishes(ALL).receive();
                    String topicName = publish.getTopic().toString();
                    String message = new String(publish.getPayloadAsBytes(), UTF_8);
                    Object fieldValue = JsonUtils.ReadPayload(message);
                    // Get the counter value
                    String counter = "-";
                    if(fieldValue != null){
                        counter = Base64Converter.base64ToHex(fieldValue.toString()).substring(0,2);
                        System.out.println("Counter: " + counter);
                    }

                    if(!Authorization.authorize(message)){
                        Authorization.logIllegalAccess(message);
                    }else if (topicName.equals("v3/lora-test-ucl@ttn/devices/eui-70b3d57ed00626b8/up")) {
                        String jsTemplate = JsonUtils.ReadJsonFile("/Users/ruibin/java/mqtt-java/src/main/java/com/locksharing/a.json");
                        String js = JsonUtils.changeCounter(jsTemplate, counter);
                        client.publishWith()
                                .topic("v3/lora-test-ucl@ttn/devices/eui-70b3d57ed00626b8/down/replace")
                                .payload(UTF_8.encode(js))
                                .send();

                        System.out.println("Published successfully");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
