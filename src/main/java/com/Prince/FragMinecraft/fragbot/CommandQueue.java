package com.Prince.FragMinecraft.fragbot;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;

import java.util.ArrayList;
import java.util.List;

public class CommandQueue {
    ArrayList<String> commands = new ArrayList<>();
    FragBot bot;
    public CommandQueue(FragBot bot) {
        this.bot = bot;
        start();
    }
    private void start(){
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tick();
            }
        }).start();
    }
    private void tick(){
        if(!commands.isEmpty()){
            String command = commands.get(0);
            bot.getClient().send(new ClientChatPacket(command));
            commands.remove(0);
        }
    }
    public void addToQueue(String command){
        commands.add(command);
    }
}
