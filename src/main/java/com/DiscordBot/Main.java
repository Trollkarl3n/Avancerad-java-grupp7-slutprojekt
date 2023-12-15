package com.DiscordBot;

import com.DiscordBot.commands.*;
import com.DiscordBot.commands.Music.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault("MTE4NDQyMDY2MjQ3NjAzNDA0OA.GQAPVl.uvILNGUzO8QphbBED2z_tKuppWe1hd3D2zjIfk").build();
        jda.addEventListener(new Listeners());
        CommandManager manager = new CommandManager();
        manager.add(new Sum());
        manager.add(new Embed());
        manager.add(new Play());
        manager.add(new Skip());
        manager.add(new Stop());
        manager.add(new NowPlaying());
        manager.add(new Queue());
        manager.add(new Repeat());
        jda.addEventListener(manager);
    }
}

