package com.picpay.simplificado.servicies;

import com.picpay.simplificado.dto.NotificationDTO;
import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.servicies.exceptions.NotificationServiceUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void notificationUserSender(User user, String message){
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);
        ResponseEntity<String> notification = restTemplate.postForEntity("https://run.mocky.io/v3/f2dad926-76af-476e-96cf-92eaee6b25f5", notificationRequest, String.class);

        if(!(notification.getStatusCode() == HttpStatus.OK)){
            System.out.println("Erro ao enviar notification");
            throw new NotificationServiceUnavailableException("Servico fora do ar");
        }
        System.out.println(message);
    }
}
