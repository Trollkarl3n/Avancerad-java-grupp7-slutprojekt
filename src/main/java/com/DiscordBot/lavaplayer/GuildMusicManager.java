package com.DiscordBot.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;

// GuildMusicManager är en klass som hanterar ljuduppspelning och framställning för en specifik guild (Discord-server)
public class GuildMusicManager {

    // En instans av TrackScheduler för att hantera uppspelning av ljudspår
    private TrackScheduler trackScheduler;

    // En instans av AudioForwarder för att framställa ljudet till guildens ljudkanal
    private AudioForwarder audioForwarder;

    // Konstruktor som tar en AudioPlayerManager och en Guild som parameter
    public GuildMusicManager(AudioPlayerManager manager, Guild guild) {
        // Skapa en ny AudioPlayer för guilden
        AudioPlayer player = manager.createPlayer();

        // Skapa en TrackScheduler för att hantera uppspelning av ljudspår
        trackScheduler = new TrackScheduler(player);

        // Lägg till TrackScheduler som lyssnare till AudioPlayer
        player.addListener(trackScheduler);

        // Skapa en AudioForwarder för att framställa ljudet till guildens ljudkanal
        audioForwarder = new AudioForwarder(player, guild);
    }

    // Getter-metod för att hämta TrackScheduler
    public TrackScheduler getTrackScheduler() {
        return trackScheduler;
    }

    // Getter-metod för att hämta AudioForwarder
    public AudioForwarder getAudioForwarder() {
        return audioForwarder;
    }
}
