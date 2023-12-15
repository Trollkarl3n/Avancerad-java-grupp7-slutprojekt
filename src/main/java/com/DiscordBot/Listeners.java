package com.DiscordBot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import org.jetbrains.annotations.NotNull;

public class Listeners extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        // Hantera knappinteraktioner
        if(event.getButton().getId().equals("yes-button")) {
            event.reply("Nice, so do I").queue();
        } else if(event.getButton().getId().equals("no-button")) {
            event.reply("What! you monster").queue();
        }
        // Ta bort det meddelande där knappen trycktes
        event.getMessage().delete().queue();
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        // Hantera modalinteraktioner
        if(event.getModalId().equals("person-modal")) {
            // Hämta värden från olika fält i modalen
            ModalMapping nameValue = event.getValue("name-field");
            ModalMapping ageValue = event.getValue("age-field");
            ModalMapping descriptionValue = event.getValue("description-field");
            // Hämta strängvärden från modalens fält
            String name = nameValue.getAsString();
            String description = descriptionValue.getAsString();

            // Hantera ålder: om åldern är tom, sätt den som "N/A", annars använd det givna värdet
            String age;
            if(ageValue.getAsString().isBlank()) {
                age = "N/A";
            } else {
                age = ageValue.getAsString();
            }
            // Skapa en EmbedBuilder för att skapa ett snyggt inbäddat meddelande
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(name);
            builder.setDescription("The description of " + name);
            builder.addField("Name", name, false);
            builder.addField("Age", age, false);
            builder.addField("Description", description, false);
            // Svara med det inbäddade meddelandet
            event.replyEmbeds(builder.build()).queue();
        }
    }
}