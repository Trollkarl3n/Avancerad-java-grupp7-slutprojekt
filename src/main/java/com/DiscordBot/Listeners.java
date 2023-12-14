package com.DiscordBot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

public class Listeners extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event){
        Guild guild = event.getJDA().getGuildById("1184427537133211698");
        guild.upsertCommand("sum", "Gives the sum of two numbers").addOptions(new OptionData(OptionType.INTEGER, "number1", "The first number", true).setMinValue(1).setMaxValue(100)).queue();
    }


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        try {
            if (event.getAuthor().isBot()) return;
            MessageChannelUnion channel = event.getChannel();
            channel.sendMessage(event.getMessage().getContentRaw()).queue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
