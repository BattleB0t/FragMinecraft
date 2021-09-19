package com.Prince.FragMinecraft.fragbot;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;

import java.util.ArrayList;
import java.util.List;

public class CommandQueue {
    ArrayList<String> commands = new ArrayList<>();
    FragBot bot;
    private int counter = 0;
    private int housingCdCounter = 0;
    private Thread thr;
    public CommandQueue(FragBot bot) {
        this.bot = bot;
        start();
    }
    private void start(){
        thr = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                    if(housingCdCounter!=0){
                        housingCdCounter--;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tick();
            }
        });
        thr.start();
    }
    public void sendToHousing(){
        if(housingCdCounter==0){
            housingCdCounter = 20;
            System.out.println("Sending to housing Lobby");
            bot.getCommandQueue().addToQueue("/lobby housing");
        }
    }
    public void stop(){
        thr.stop();
    }
    private void tick(){
        counter++;
        if(!commands.isEmpty()){
            String command = commands.get(0);
            bot.getClient().send(new ClientChatPacket(command));
            commands.remove(0);
        }
        if(counter>=300){
            System.out.println("Bot has exited limbo (hopefully)");
            commands.add("/lobby housing");
            counter=0;
        }
    }
    public void addToQueue(String command){
        commands.add(command);
    }
}
