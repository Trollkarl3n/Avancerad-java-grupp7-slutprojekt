package com.DiscordBot.commands.Music;

import com.DiscordBot.ICommand;
import com.DiscordBot.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Play implements ICommand {
    @Override  // Metod för att hämta kommandonamnet
    public String getName() {
        return "play";
    }

    @Override  // Metod för att hämta beskrivningen av kommandot
    public String getDescription() {
        return "Will play a song";
    }

    @Override // Metod för att hämta kommandoalternativ (options) för Slash-kommandot
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "name", "Name of the song to play", true));
        return options;
    }

    @Override // Metod som utför kommandot när det anropas
    public void execute(SlashCommandInteractionEvent event) {
        // Hämta information om den användare som anropade kommandot
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();
        // Kontrollera om användaren inte är i en röstkanal
        if(!memberVoiceState.inAudioChannel()) {
            event.reply("You need to be in a voice channel").queue();
            return;
        }
        // Hämta information om botten själv (den egna botten)
        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();
        // Kontrollera om botten inte är i en röstkanal
        if(!selfVoiceState.inAudioChannel()) {
            // Öppna en ljudanslutning till den röstkanal som användaren är i
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
            // Kontrollera om användaren och botten är i olika röstkanaler
        } else {
            if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.reply("You need to be in the same channel as me").queue();
                return;
            }
        }
        // Hämta namnet på låten från kommandots options och hantera det som en URI
        String name = event.getOption("name").getAsString();
        try {
            new URI(name);
        } catch (URISyntaxException e) {
            // Om det inte är en URI, antas det vara en sökning på YouTube
            name = "ytsearch:" + name;
        }
        // Hämta PlayerManager-instansen och spela upp låten
        PlayerManager playerManager = PlayerManager.get();
        event.reply("Playing").queue();
        playerManager.play(event.getGuild(), name);
    }
}