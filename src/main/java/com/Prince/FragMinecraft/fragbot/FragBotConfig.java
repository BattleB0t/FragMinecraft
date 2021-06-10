package com.Prince.FragMinecraft.fragbot;

public class FragBotConfig {
    private int waitTime;
    private String botName;
    private String webhookUrl;
    public FragBotConfig(int waitTime, String botName, String webhookUrl) {
        this.waitTime = waitTime;
        this.botName = botName;
        this.webhookUrl = webhookUrl;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public String getBotName() {
        return botName;
    }
}
