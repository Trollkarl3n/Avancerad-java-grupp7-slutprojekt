package com.DiscordBot.commands.Music;

import com.DiscordBot.ICommand;
import com.DiscordBot.lavaplayer.GuildMusicManager;
import com.DiscordBot.lavaplayer.PlayerManager;
import com.DiscordBot.lavaplayer.TrackScheduler;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class Stop implements ICommand {
    @Override // Metod för att hämta kommandonamnet
    public String getName() {
        return "stop";
    }

    @Override // Metod för att hämta beskrivningen av kommandot
    public String getDescription() {
        return "Will stop the bot playing";
    }

    @Override // Metod för att hämta kommandoalternativ (options) för Slash-kommandot
    public List<OptionData> getOptions() {
        return null;
    }

    @Override  // Metod som utför kommandot när det anropas
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
            event.reply("I am not in an audio channel").queue();
            return;
        }
// Kontrollera om användaren och botten är i olika röstkanaler
        if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
            event.reply("You are not in the same channel as me").queue();
            return;
        }
// Hämta GuildMusicManager för den aktuella guilden (Discord-servern)
        GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
        // Hämta TrackScheduler för att hantera uppspelning av ljudspår
        TrackScheduler trackScheduler = guildMusicManager.getTrackScheduler();
        // Rensa ljudspårskön och stoppa det aktuella ljudspåret
        trackScheduler.getQueue().clear();
        trackScheduler.getPlayer().stopTrack();
        // Svara med ett meddelande som indikerar att uppspelningen har stoppats
        event.reply("Stopped").queue();
    }
}