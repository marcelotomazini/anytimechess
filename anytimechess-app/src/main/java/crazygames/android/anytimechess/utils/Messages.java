package crazygames.android.anytimechess.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class Messages {

	// property file is: package/name/messages.properties
	private static final String BUNDLE_NAME = "MessagesBundle";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getString(String key, Object... params) {
		try {
			return MessageFormat.format(RESOURCE_BUNDLE.getString(key), params);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
