package com.DiscordBot.commands.Music;

import com.DiscordBot.ICommand;
import com.DiscordBot.lavaplayer.GuildMusicManager;
import com.DiscordBot.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class Queue implements ICommand {
    @Override  // Metod för att hämta kommandonamnet
    public String getName() {
        return "queue";
    }

    @Override  // Metod för att hämta beskrivningen av kommandot
    public String getDescription() {
        return "Will display the current queue";
    }

    @Override // Metod för att hämta kommandoalternativ (options) för Slash-kommandot
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
        // Hämta ljudspårskön från TrackScheduler
        List<AudioTrack> queue = new ArrayList<>(guildMusicManager.getTrackScheduler().getQueue());
        // Skapa en EmbedBuilder för att skapa en snygg inbäddad (embedded)
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Current Queue");
        // Om kön är tom, lägg till en beskrivning om att kön är tom
        if(queue.isEmpty()) {
            embedBuilder.setDescription("Queue is empty");
        }
        // Loopa genom kön och lägg till varje ljudspår i inbäddningen
        for(int i = 0; i < queue.size(); i++) {
            AudioTrackInfo info = queue.get(i).getInfo();
            embedBuilder.addField(i+1 + ":", info.title, false);
        }
        // Svara med inbäddad svarsmeddelande som visar kön
        event.replyEmbeds(embedBuilder.build()).queue();
    }
}