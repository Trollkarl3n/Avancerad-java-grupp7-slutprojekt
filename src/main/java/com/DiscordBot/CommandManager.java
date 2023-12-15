package com.DiscordBot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

// CommandManager är en ListenerAdapter som hanterar registrering av slash-kommandon och utförandet av dessa kommandon
public class CommandManager extends ListenerAdapter {

    // En lista som lagrar alla registrerade kommandon
    private List<ICommand> commands = new ArrayList<>();

    // Metod som körs när boten är redo att användas
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        // Loopa genom varje guild (server) där boten är ansluten
        for(Guild guild : event.getJDA().getGuilds()) {
            // Loopa genom varje registrerat kommando
            for(ICommand command : commands) {
                // Om kommandot inte har några alternativ (options), registrera det utan alternativ
                if(command.getOptions() == null) {
                    guild.upsertCommand(command.getName(), command.getDescription()).queue();
                } else {
                    // Annars, om kommandot har alternativ, registrera det med dessa alternativ
                    guild.upsertCommand(command.getName(), command.getDescription()).addOptions(command.getOptions()).queue();
                }
            }
        }
    }

    // Metod som körs när ett slash-kommando interageras med
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        // Loopa genom varje registrerat kommando
        for(ICommand command : commands){
            // Om kommandonamnet matchar det inkommande slash-kommandot, utför kommandot
            if(command.getName().equals(event.getName())){
                command.execute(event);
                return;
            }
        }
    }

    // Metod för att lägga till ett kommando i listan över registrerade kommandon
    public void add(ICommand command) {
        commands.add(command);
    }
}