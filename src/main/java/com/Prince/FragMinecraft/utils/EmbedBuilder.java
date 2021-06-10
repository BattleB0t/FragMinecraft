package com.Prince.FragMinecraft.utils;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.Prince.FragMinecraft.fragbot.FragBot;

import java.awt.*;
import java.util.Date;

public class EmbedBuilder {
    WebhookEmbedBuilder builder;
    public EmbedBuilder(FragBot bot){
        builder = new WebhookEmbedBuilder();
        //builder.setAuthor(new WebhookEmbed.EmbedAuthor(bot.getConfig().getBotName(),"https://cdn.discordapp.com/icons/816493066910302240/a7df39ddcf7b3b268e0babbea7c68f73.jpg",null));
        builder.setFooter(new WebhookEmbed.EmbedFooter("discord.gg/fragbots","https://cdn.discordapp.com/icons/816493066910302240/a7df39ddcf7b3b268e0babbea7c68f73.jpg"));
        builder.setColor(new Color(68, 160, 235).getRGB());
        builder.setTitle(new WebhookEmbed.EmbedTitle(bot.getBotName()+" Logs",null));
        builder.setTimestamp(new Date().toInstant());
    }
    public EmbedBuilder setDescription(String description){
        builder.setDescription(description);
        return this;
    }
    public EmbedBuilder setImage(String image){
        builder.setThumbnailUrl(image);
        return this;
    }
    public WebhookEmbed build() {
        return builder.build();
    }
}
