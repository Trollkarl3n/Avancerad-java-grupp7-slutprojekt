package com.DiscordBot.commands;


import com.DiscordBot.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.List;

public class Embed implements ICommand {


    @Override
    public String getName() {
        return "embed";
    }

    @Override
    public String getDescription() {
        return "Send embed";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Embed");

        builder.setDescription("Example embed");
        builder.addField("Field 1", "Value", false);
        builder.addField("Field 2", "Value", false);
        builder.addField("Field 3", "Value", false);
        builder.setFooter("Author: Neil & Erik");
        builder.setColor(Color.CYAN);
        event.replyEmbeds(builder.build()).queue();

    }
}
