package ir.xenoncommunity.utils;

import org.jetbrains.annotations.Nullable;

public class CommandLineParser {

	private final String[] args;

	public CommandLineParser(String[] args) {
		this.args = args;
	}

	@Nullable
	public <T> T get(String arg, Class<T> type) {
		for (int i = 0; i < args.length - 1; i++) {
			if (args[i].equals(arg))
				return convertToType(args[i + 1], type);
		}
		return null;
	}

	private <T> T convertToType(String value, Class<T> type) {
		if (type.equals(String.class)) {
			return type.cast(value);
		} else if (type.equals(Integer.class)) {
			return type.cast(Integer.valueOf(value));
		} else if (type.equals(Boolean.class)) {
			return type.cast(Boolean.valueOf(value));
		} else {
			throw new IllegalArgumentException("Unsupported type");
		}
	}
}