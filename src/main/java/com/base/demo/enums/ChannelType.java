package com.base.demo.enums;

public enum ChannelType {

    MESSAGE("message"),
    ACK("ack");

    private final String channel;

    ChannelType(String channel) {
        this.channel = channel;
    }

    public String getValue() {
        return channel;
    }
}
