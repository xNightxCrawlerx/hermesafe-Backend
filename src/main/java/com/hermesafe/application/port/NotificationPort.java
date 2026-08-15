package com.hermesafe.application.port;

public interface NotificationPort {
    void notifyOrderProcessed(String orderId, String status);
}
