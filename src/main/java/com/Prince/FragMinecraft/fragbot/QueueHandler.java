package com.Prince.FragMinecraft.fragbot;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.Prince.FragMinecraft.minecraftevents.events.FragBotJoinEvent;
import com.Prince.FragMinecraft.utils.EmbedBuilder;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;

import java.util.ArrayList;
import java.util.List;

public class QueueHandler {
    List<String> queueIgns;
    long botLeaveTimeStamp=0;
    String inPartyWith;
    FragBot bot;
    public QueueHandler(FragBot bot){
        this.bot = bot;
        queueIgns = new ArrayList<String>();
        start();
    }
    private void start(){
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tick();
            }
        }).start();
    }
    private void tick(){
        if(queueIgns.size()>0&&botLeaveTimeStamp==0){
            botLeaveTimeStamp = System.currentTimeMillis() + bot.getConfig().getWaitTime();
            inPartyWith = queueIgns.get(0);
            joinParty(queueIgns.get(0));
            queueIgns.remove(0);
            bot.getEventHandler().callEvent(new FragBotJoinEvent(inPartyWith,bot));
            bot.log("Joined party of player: "+inPartyWith);
            bot.getWebhookClient().send(new EmbedBuilder(bot).setDescription(""+bot.getBotName()+" has joined "+inPartyWith+"'s party!\nQueue Length: "+queueIgns.size()+"").setImage("https://mc-heads.net/avatar/"+inPartyWith+"/100").build());
        }else{
            if(System.currentTimeMillis()>=botLeaveTimeStamp&&botLeaveTimeStamp!=0){
                botLeaveTimeStamp = 0;
                leaveParty();
                bot.log("Left party of player: "+inPartyWith);
                inPartyWith = null;
            }
        }
    }
    public void joinParty(String ign){
        bot.getClient().send(new ClientChatPacket("/party join "+ign));
    }
    public void leaveParty(){
        bot.getClient().send(new ClientChatPacket("/party leave"));
    }
    public void addToQueue(String ign){
        if(queueIgns.contains(ign)||queueIgns.size()*bot.getConfig().getWaitTime()+bot.getConfig().getWaitTime()>60000) return;
        queueIgns.add(ign);
    }
}
