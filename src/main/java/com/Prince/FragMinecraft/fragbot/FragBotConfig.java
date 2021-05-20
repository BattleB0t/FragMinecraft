package com.Prince.FragMinecraft.fragbot;

public class FragBotConfig {
    private int waitTime;
    private String botName;
    public FragBotConfig(int waitTime, String botName) {
        this.waitTime = waitTime;
        this.botName = botName;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public String getBotName() {
        return botName;
    }
}
