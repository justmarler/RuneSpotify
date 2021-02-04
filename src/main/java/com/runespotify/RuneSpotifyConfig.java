package com.runespotify;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(RuneSpotifyConfig.GROUP)
public interface RuneSpotifyConfig extends Config
{
	String GROUP = "runespotify";

	@ConfigItem(
		keyName = "userAuthentication",
		name = "Spotify User Authentication",
		description = "Activate Spotify user authentication."
	)
	default boolean userAuthentication()
	{
		return false;
	}
}
