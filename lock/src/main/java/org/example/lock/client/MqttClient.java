package org.example.lock.client;

public class MqttClient {
    public void changeReportBattery(Long id, int reportBattery) {
        System.out.println("MqttClient.changeReportBattery");
    }

    public void changeReportLocation(Long id, int reportLocation) {
        System.out.println("MqttClient.changeReportLocation");
    }
}
