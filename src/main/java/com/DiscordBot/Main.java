package com.DiscordBot;

import com.DiscordBot.commands.Sum;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {
    public static void main (String[] args){
        JDA jda = JDABuilder.createDefault("MTE4NDQyMDY2MjQ3NjAzNDA0OA.GQAPVl.uvILNGUzO8QphbBED2z_tKuppWe1hd3D2zjIfk").build();
        jda.addEventListener(new Listeners());
        jda.addEventListener(new Sum());

        /*
            Global commands - Can be used anywere dms etc (Takes long time to register hour+)
            Guild commands - Can only be used in sertain guilds (Servers)
         */
    }
}

