package com.DiscordBot.commands.Music;

import com.DiscordBot.ICommand;
import com.DiscordBot.lavaplayer.GuildMusicManager;
import com.DiscordBot.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class Repeat implements ICommand {
    @Override  // Metod för att hämta kommandonamnet
    public String getName() {
        return "repeat";
    }

    @Override // Metod för att hämta beskrivningen av kommandot
    public String getDescription() {
        return "Will toggle repeating";
    }

    @Override  // Metod för att hämta kommandoalternativ (options) för Slash-kommandot
    public List<OptionData> getOptions() {
        return null;
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
        // Hämta nuvarande upprepningstatus och växla den
        boolean isRepeat = !guildMusicManager.getTrackScheduler().isRepeat();
        guildMusicManager.getTrackScheduler().setRepeat(isRepeat);
        // Svara med ett meddelande som indikerar den nya upprepningstatusen
        event.reply("Repeat is now " + isRepeat).queue();
    }
}