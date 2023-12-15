package com.DiscordBot.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

// AudioForwarder implementerar AudioSendHandler för att framställa ljudet till guildens ljudkanal
public class AudioForwarder implements AudioSendHandler {

    // En instans av AudioPlayer för att spela upp ljud
    private final AudioPlayer player;

    // En instans av Guild (Discord-server) för att hantera ljudkanalen
    private final Guild guild;

    // En ByteBuffer för att lagra ljuddata
    private final ByteBuffer buffer = ByteBuffer.allocate(1024);

    // En instans av MutableAudioFrame för att hantera ljudramen
    private final MutableAudioFrame frame = new MutableAudioFrame();

    // En räknare för att övervaka tiden sedan senaste ljudframställning
    private int time;

    // Konstruktor som tar en AudioPlayer och en Guild som parameter
    public AudioForwarder(AudioPlayer player, Guild guild) {
        this.player = player;
        this.guild = guild;
        frame.setBuffer(buffer);
    }

    // Metod som avgör om ljud kan tillhandahållas för framställning
    @Override
    public boolean canProvide() {
        // Försök att tillhandahålla ljud och övervaka tiden sedan senaste ljudframställning
        boolean canProvide = player.provide(frame);
        if(!canProvide) {
            time += 20;
            // Om tiden överstiger 300000 millisekunder (5 minuter), stäng ljudkanalen
            if(time >= 300000) {
                time = 0;
                guild.getAudioManager().closeAudioConnection();
            }
        } else {
            // Återställ tiden om ljud framställs
            time = 0;
        }
        return canProvide;
    }

    // Metod för att tillhandahålla ljud för varje 20 millisekunder
    @Nullable
    @Override
    public ByteBuffer provide20MsAudio() {
        return buffer.flip();
    }

    // Metod som returnerar om ljudet är i Opus-format
    @Override
    public boolean isOpus() {
        return true;
    }
}