package com.DiscordBot.commands;

import com.DiscordBot.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

// Sum är en implementation av ICommand som hanterar en slash-kommando för att beräkna summan av två tal
public class Sum implements ICommand {

    // Getter för kommandonamnet
    @Override
    public String getName() {
        return "sum";
    }

    // Getter för beskrivningen av kommandot
    @Override
    public String getDescription() {
        return "Will take the sum of two numbers";
    }

    // Getter för kommandots optioner (parametrar)
    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();

        // Lägg till en option för det första talet
        data.add(new OptionData(OptionType.INTEGER, "number1", "The first number", true)
                .setMinValue(1)
                .setMaxValue(100));

        // Lägg till en option för det andra talet
        data.add(new OptionData(OptionType.INTEGER, "number2", "The second number", false)
                .setMinValue(1)
                .setMaxValue(100));

        return data;
    }

    // Metod för att utföra kommandot baserat på mottagna parametrar
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        // Hämta det första talet från kommandoparametern "number1"
        OptionMapping number1 = event.getOption("number1");
        int num1 = number1.getAsInt();

        // Hämta det andra talet från kommandoparametern "number2", om det finns, annars sätt det till 1
        OptionMapping number2 = event.getOption("number2");
        int num2 = (number2 != null) ? number2.getAsInt() : 1;

        // Beräkna summan av de två talen
        int result = num1 + num2;

        // Skicka resultatet som svar på kommandot
        event.reply(result + "").queue();
    }
}