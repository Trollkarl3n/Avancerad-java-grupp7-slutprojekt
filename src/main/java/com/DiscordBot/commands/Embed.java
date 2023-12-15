package com.DiscordBot.commands;

import com.DiscordBot.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.List;

// Embed är en implementation av ICommand som hanterar en slash-kommando för att skicka en inbäddad (embed) meddelande
public class Embed implements ICommand {

    // Getter för kommandonamnet
    @Override
    public String getName() {
        return "embed";
    }

    // Getter för beskrivningen av kommandot
    @Override
    public String getDescription() {
        return "Will send an embed";
    }

    // Getter för kommandots optioner (parametrar)
    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    // Metod för att utföra kommandot baserat på mottagna parametrar
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        // Skapa en EmbedBuilder för att bygga det inbäddade meddelandet
        EmbedBuilder builder = new EmbedBuilder();

        // Ange titel och beskrivning för det inbäddade meddelandet
        builder.setTitle("Example Embed");
        builder.setDescription("An example embed");

        // Lägg till fält i det inbäddade meddelandet
        builder.addField("Field 1", "Value", false);
        builder.addField("Field 2", "Value", false);
        builder.addField("Field 3", "Value", false);

        // Ange en footer för det inbäddade meddelandet
        builder.setFooter("Example Footer");

        // Ange färgen för det inbäddade meddelandet
        builder.setColor(Color.BLUE);

        // Lägg till ytterligare beskrivning till det inbäddade meddelandet
        builder.appendDescription(" This has been added");

        // Ange författaren för det inbäddade meddelandet
        builder.setAuthor("Erik Werther & Neil Elvirsson");

        // Skicka det inbäddade meddelandet som svar på kommandot
        event.replyEmbeds(builder.build()).queue();
    }
}