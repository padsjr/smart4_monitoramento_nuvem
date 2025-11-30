package br.edu.senai.smart4.monitoramento.Controller;

import br.edu.senai.smart4.monitoramento.services.MqttService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final MqttService mqttService;

    public DashboardController(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    @GetMapping("/dados")
    public String getDados() {
        return mqttService.getLastMessage();
    }

    @GetMapping("/dashboard")
    public String getDashboard() {
        return """
        <html>
            <body>
                <h1>Smart 4.0 – Dados em Tempo Real</h1>
                <div id="content"></div>

                <script>
                    async function atualizar() {
                        const resp = await fetch('/dados');
                        const dados = await resp.text();
                        document.getElementById('content').innerHTML =
                            "<pre>" + dados + "</pre>";
                    }
                    atualizar();
                    setInterval(atualizar, 2000);
                </script>
            </body>
        </html>
        """;
    }
}
