package com.DiscordBot.commands.Music;

import com.DiscordBot.ICommand;
import com.DiscordBot.lavaplayer.GuildMusicManager;
import com.DiscordBot.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class NowPlaying implements ICommand {
    @Override  // Metod för att hämta kommandonamnet
    public String getName() {
        return "nowplaying";
    }

    @Override // Metod för att hämta beskrivningen av kommandot
    public String getDescription() {
        return "Will display the current playing song";
    }

    @Override  // Metod för att hämta kommandoalternativ (options) för Slash-kommandot
    public List<OptionData> getOptions() {
        return null;
    }


    @Override
    public void execute(SlashCommandInteractionEvent event) {
        //Hämtar medlem och röststatus för den som anropat kommandot
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();
        //Kontrollerar om medlem är i röst kanalen
        if(!memberVoiceState.inAudioChannel()) {
            event.reply("You need to be in a voice channel").queue();
            return;
        }
        // Hämtar information om botten själv
        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();
        //Kontrollerar om botten är i röstkanal
        if(!selfVoiceState.inAudioChannel()) {
            event.reply("I am not in an audio channel").queue();
            return;
        }
        //Kontrollerar om medlem och botten är i samma röstkanal
        if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
            event.reply("You are not in the same channel as me").queue();
            return;
        }
        //Hämta GuildMusicManager för den aktuella guilden (Discord-servern)
        GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
        if(guildMusicManager.getTrackScheduler().getPlayer().getPlayingTrack() == null) {
            event.reply("I am not playing anything").queue();
            return;
        }
        // Hämta information om det aktuellt uppspelade ljudspåret
        AudioTrackInfo info = guildMusicManager.getTrackScheduler().getPlayer().getPlayingTrack().getInfo();
        // // Skapa en EmbedBuilder för att skapa en snygg inbäddad (embedded)
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Currently Playing");
        embedBuilder.setDescription("**Name:** `" + info.title + "`");
        embedBuilder.appendDescription("\n**Author:** `" + info.author + "`");
        embedBuilder.appendDescription("\n**URL:** `" + info.uri + "`");
        event.replyEmbeds(embedBuilder.build()).queue();
    }
}