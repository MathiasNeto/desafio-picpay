package com.picpay.simplificado.servicies;

import com.picpay.simplificado.dto.TransactionDTO;
import com.picpay.simplificado.dto.UserDTO;
import com.picpay.simplificado.entities.Transaction;
import com.picpay.simplificado.entities.User;
import com.picpay.simplificado.enums.UserType;
import com.picpay.simplificado.respositories.TransactionRepository;

import java.math.BigDecimal;
import java.util.Map;

import com.picpay.simplificado.servicies.exceptions.ForbiddenException;
import com.picpay.simplificado.servicies.exceptions.FoundsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public TransactionDTO createTransaction(TransactionDTO dto) {
        UserDTO received = userService.findUserById(dto.getReceived_id());
        UserDTO sender = userService.findUserById(dto.getSender_id());

        User userSender = new User(sender);
        User userReceived = new User(received);

        validationTransaction(sender, dto.getAmount(), dto);

        boolean isAuthorized = authorizedTransaction();
        if (!isAuthorized) throw new ForbiddenException("Erro na transacao, nao autorizada");

        Transaction transaction = new Transaction();
        transaction.setTransactionMoment(LocalDateTime.now());
        transaction.setAmount(dto.getAmount());
        transaction.setSender(userSender);
        transaction.setReceived(userReceived);

        sender.setBankAccount(userSender.getBankAccount().subtract(transaction.getAmount()));
        received.setBankAccount(userSender.getBankAccount().add(transaction.getAmount()));

        userService.update(dto.getSender_id(), sender);
        userService.update(dto.getReceived_id(), received);

        notificationService.notificationUserSender(userSender, "Transacao realizada");
        notificationService.notificationUserSender(userReceived, "Transacao recebida");

        transactionRepository.save(transaction);

        return new TransactionDTO(transaction);


    }

    public boolean authorizedTransaction(){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc",Map.class);

        if(authorizationResponse.getStatusCode() == HttpStatus.OK){
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }
        return false;
    }

    @Transactional
    public void validationTransaction(UserDTO sender, BigDecimal amount, TransactionDTO transaction) {
        User user = new User();
        BeanUtils.copyProperties(sender, user);

        UserDTO receiverDto = userService.findUserById(transaction.getReceived_id());
        UserDTO senderDto = userService.findUserById(transaction.getSender_id());

        UserType receiverType = receiverDto.getUserType();
        UserType senderType = senderDto.getUserType();

        boolean isReceiverCommon = receiverType == UserType.COMMON;
        boolean isSenderShopkeeper = senderType == UserType.SHOPKEEPER;

        if (isReceiverCommon || isSenderShopkeeper)  {
            throw new ForbiddenException("Usuario nao autorizado para enviar ou receber dinheiro");
        }
        if (sender.getBankAccount().compareTo(amount) < 0) {
            throw new FoundsException("Saldo insuficiente ou valor da transferencia inferior a R$1.00");
        }
    }
}
