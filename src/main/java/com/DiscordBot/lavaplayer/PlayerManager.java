package com.DiscordBot.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;

// PlayerManager är en singleton-klass som hanterar ljuduppspelning för olika guilds (Discord-servrar)
public class PlayerManager {

    // Singleton-instans
    private static PlayerManager INSTANCE;

    // En karta (Map) som lagrar GuildMusicManager för varje guild
    private Map<Long, GuildMusicManager> guildMusicManagers = new HashMap<>();

    // AudioPlayerManager för hantering av ljuduppspelning
    private AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();

    // Privat konstruktor som används för att förhindra direkt instansiering
    private PlayerManager() {
        // Registrera ljudkällor för hantering av fjärr- och lokal musik
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
    }

    // Singleton-getter för att hämta PlayerManager-instansen
    public static PlayerManager get() {
        if(INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }

    // Metod för att hämta eller skapa en GuildMusicManager för en specifik guild
    public GuildMusicManager getGuildMusicManager(Guild guild) {
        return guildMusicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            // Skapa en ny GuildMusicManager om den inte redan finns för guilden
            GuildMusicManager musicManager = new GuildMusicManager(audioPlayerManager, guild);

            // Sätt AudioPlayer-sändningshanteraren för guildens AudioManager
            guild.getAudioManager().setSendingHandler(musicManager.getAudioForwarder());

            return musicManager;
        });
    }

    // Metod för att spela upp ett ljudspår i en guild
    public void play(Guild guild, String trackURL) {
        GuildMusicManager guildMusicManager = getGuildMusicManager(guild);

        // Ladda ljudspåret eller spellistan asynkront och hantera resultatet med en AudioLoadResultHandler
        audioPlayerManager.loadItemOrdered(guildMusicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                // Lägg till ljudspåret i guildens kö för uppspelning
                guildMusicManager.getTrackScheduler().queue(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                // Om det är en spellista, lägg till det första ljudspåret i guildens kö för uppspelning
                guildMusicManager.getTrackScheduler().queue(playlist.getTracks().get(0));
            }

            @Override
            public void noMatches() {
                // Ingen matchning hittades
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                // Laddningen misslyckades, hantera fel
            }
        });
    }
}