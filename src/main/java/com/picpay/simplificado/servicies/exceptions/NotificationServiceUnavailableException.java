package com.picpay.simplificado.servicies.exceptions;

import javax.sql.rowset.spi.SyncResolver;

public class NotificationServiceUnavailableException extends RuntimeException{

    public NotificationServiceUnavailableException(String msg){
        super(msg);
    }
}
