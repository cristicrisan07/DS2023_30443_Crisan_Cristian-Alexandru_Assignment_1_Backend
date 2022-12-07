package com.example.online_energy_utility_platform_backend.service;

import com.example.online_energy_utility_platform_backend.model.DeviceConsumptionAlertDTO;
import com.example.online_energy_utility_platform_backend.model.EnergyConsumption;
import com.example.online_energy_utility_platform_backend.model.MeteringDevice;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class MessageOperationsService {

    private final static String QUEUE_NAME = "assignment_queue";
    private int readingNbInAnHour=0;
    private Double totalHourConsumption=0.0;
    @Autowired
    private MeteringDeviceOperationsService meteringDeviceOperationsService;
    @Autowired
    private EnergyConsumptionOperationsService energyConsumptionOperationsService;
    @Autowired
    private DTOConverter dtoConverter;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

//    public MessageOperationsService(com.example.online_energy_utility_platform_backend.service.MeteringDeviceOperationsService meteringDeviceOperationsService, EnergyConsumptionOperationsService energyConsumptionOperationsService, DTOConverter dtoConverter, SimpMessagingTemplate simpMessagingTemplate) {
//        this.meteringDeviceOperationsService = meteringDeviceOperationsService;
//        this.energyConsumptionOperationsService = energyConsumptionOperationsService;
//        this.dtoConverter = dtoConverter;
//        this.simpMessagingTemplate = simpMessagingTemplate;
//    }
    @RabbitListener(queues = "assignment_queue")
    public void consumeMessages() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("host.docker.internal");
        //factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConfig.QUEUE, true, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            JSONParser parser = new JSONParser();

           try {

                JSONObject jsonObject = (JSONObject) parser.parse(message);

                System.out.println(" [x] Received '" + jsonObject + "'");

                double readingValue = Double.parseDouble(jsonObject.get("measurementValue").toString());

                readingNbInAnHour++;
                totalHourConsumption+=readingValue;

                if(readingNbInAnHour>6){

                    String readingTimestamp = (String) jsonObject.get("timestamp");
                    Long deviceId = (Long) jsonObject.get("deviceId");

                    MeteringDevice meteringDevice= meteringDeviceOperationsService.getMeteringDeviceWithId(deviceId);
                    EnergyConsumption consumption = new EnergyConsumption(readingTimestamp,readingValue);

                    consumption.setMeteringDevice(meteringDevice);

                    if(totalHourConsumption>meteringDevice.getMax_hour_energy_cons()) {

                        energyConsumptionOperationsService.createEnergyConsumptionEntry(consumption);

                        String finalMessage = "Maximum hourly consumption for device with id: " + meteringDevice.getId() + "\n with description: " + meteringDevice.getDescription() + " has been exceeded.\n" +
                                         "The limit is: " + meteringDevice.getMax_hour_energy_cons() + " and the device recorded a consumption of: " + totalHourConsumption.toString() + " at: " + Integer.toString(LocalDateTime.now().getHour());

                        DeviceConsumptionAlertDTO deviceConsumptionAlertDTO = new DeviceConsumptionAlertDTO(finalMessage,meteringDevice.getClient().getUsername());

                        simpMessagingTemplate.convertAndSend("/topic/message", deviceConsumptionAlertDTO);
                    }

                    readingNbInAnHour=0;
                    totalHourConsumption = 0.0;

              }

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
    };

      channel.basicConsume(RabbitConfig.QUEUE, true, deliverCallback, consumerTag -> {
       });
    }
}
