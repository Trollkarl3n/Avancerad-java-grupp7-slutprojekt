package com.DiscordBot.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// TrackScheduler är en klass som hanterar uppspelning och kö av ljudspår för en AudioPlayer
public class TrackScheduler extends AudioEventAdapter {

    // AudioPlayer för hantering av uppspelning
    private final AudioPlayer player;

    // En kö (BlockingQueue) för att lagra ljudspår som ska spelas upp
    private final BlockingQueue<AudioTrack> queue = new LinkedBlockingQueue<>();

    // En flagga som indikerar om upprepning (repeat) är aktiverad
    private boolean isRepeat = false;

    // Konstruktor som tar en AudioPlayer som parameter
    public TrackScheduler(AudioPlayer player) {
        this.player = player;
    }

    // Metod som körs när ett ljudspår har avslutats
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Om upprepning är aktiverad startas samma spår igen, annars hämtas nästa spår från kön
        if(isRepeat){
            player.startTrack(track.makeClone(), false);
        } else {
            player.startTrack(queue.poll(), false);
        }
    }

    // Metod för att lägga till ett ljudspår i kön
    public void queue(AudioTrack track) {
        // Om det går att starta ljudspåret direkt läggs det inte i kön, annars läggs det i kön
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    // Getter-metod för att hämta AudioPlayer
    public AudioPlayer getPlayer() {
        return player;
    }

    // Getter-metod för att hämta kön av ljudspår
    public BlockingQueue<AudioTrack> getQueue() {
        return queue;
    }

    // Getter-metod för att hämta om upprepning är aktiverad
    public boolean isRepeat() {
        return isRepeat;
    }

    // Setter-metod för att sätta upprepning (repeat)
    public void setRepeat(boolean repeat){
        isRepeat = repeat;
    }
}
