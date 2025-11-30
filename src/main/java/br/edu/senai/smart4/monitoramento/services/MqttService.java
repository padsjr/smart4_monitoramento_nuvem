package br.edu.senai.smart4.monitoramento.services;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

@Service
public class MqttService {

    private static final String BROKER = "tcp://mqtt-dashboard.com:1883";
    private static final String TOPIC = "senai/bancada/estoque/estacao1";

    private String lastMessage = "{}";

    @PostConstruct
    public void connectAndSubscribe() {
        try {
            MqttClient client = new MqttClient(BROKER, MqttClient.generateClientId());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);

            client.connect(options);

            client.subscribe(TOPIC, (topic, msg) -> {
                lastMessage = new String(msg.getPayload());
                System.out.println("Mensagem recebida via MQTT: " + lastMessage);
            });

            System.out.println("Conectado ao MQTT e inscrito ao tópico.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
