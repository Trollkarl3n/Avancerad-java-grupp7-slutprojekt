package com.DiscordBot.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.nio.ByteBuffer;

public class AudioForwarder implements AudioSendHandler {

    private final AudioPlayer player;
    private final ByteBuffer buffer = ByteBuffer.allocate(1024); // Can only send 1024bytes every 20Ms
    private final MutableAudioFrame frame = new MutableAudioFrame();

    // Uses the frame to get all the data -> that writes it to the buffer
    public AudioForwarder(AudioPlayer player) {
        this.player = player;
        frame.setBuffer(buffer);
    }

    @Override
    public boolean canProvide() { // provides information from the "player" to the frame
        return player.provide(frame); // Returns boolean to look over if got sent something or not
    }

    @Override
    public ByteBuffer provide20MsAudio() { // Sends out 20Ms of Audio and sends buffer info to discord
        // flip will set the limit of the buffer to the current position and reset the position to zero
        return buffer.flip();
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
