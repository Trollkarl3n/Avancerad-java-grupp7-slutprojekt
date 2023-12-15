package com.DiscordBot;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

// ICommand är ett interface som representerar ett Discord-kommando
public interface ICommand {
    // Metod för att hämta kommandonamnet
    String getName();
    // Metod för att hämta beskrivningen av kommandot
    String getDescription();
    // Metod för att hämta eventuella alternativ (options) för kommandot
    List<OptionData> getOptions();
    // Metod för att utföra själva kommandot när det kallas av en användare
    void execute(SlashCommandInteractionEvent event);
}
