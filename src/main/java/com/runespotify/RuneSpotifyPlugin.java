package com.runespotify;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.security.SecureRandom;
import java.security.MessageDigest;

@Slf4j
@PluginDescriptor(
	name = "RuneSpotify"
)
public class RuneSpotifyPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private RuneSpotifyConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("RuneSpotify started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("RuneSpotify stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "RuneSpotify Plugin loaded!", null);
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged configChanged) throws NoSuchAlgorithmException
	{
		if (configChanged.getGroup().equalsIgnoreCase(RuneSpotifyConfig.GROUP))
		{
			try {
				SecureRandom sr = new SecureRandom();
				byte[] code = new byte[32];
				sr.nextBytes(code);
				String codeVerifier = Base64.getEncoder().encodeToString(code);
				byte[] codeVerifierBytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(codeVerifierBytes);
				byte[] codeChallengeBytes = md.digest();
				String codeChallenge = Base64.getEncoder().encodeToString(codeChallengeBytes);
				log.info(codeChallenge);
			} catch (NoSuchAlgorithmException e) {
				e.toString();
			}
		}
	}

	@Provides
	RuneSpotifyConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RuneSpotifyConfig.class);
	}
}
