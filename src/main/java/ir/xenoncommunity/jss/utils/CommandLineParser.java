package ir.xenoncommunity.jss.utils;

import org.jetbrains.annotations.Nullable;

public class CommandLineParser {

    private final String[] args;

    public CommandLineParser(final String[] args) {
        this.args = args;
    }

    public <T> T get(final String arg, final Class<T> type, final @Nullable T defaultValue) {
        for (int i = 0; i < args.length - 1; i++) {
            if (!args[i].equals(arg)) continue;

            return args[i + 1].startsWith("-") ? type.cast(Boolean.TRUE) : convertToType(args[i + 1], type);
        }
        return defaultValue;
    }



    private <T> T convertToType(final String value, final Class<T> type) {
        if (type.equals(String.class)) {
            return type.cast(value);
        } else if (type.equals(Integer.class)) {
            return type.cast(Integer.parseInt(value));
        } else if (type.equals(Boolean.class)) {
            return type.cast(Boolean.parseBoolean(value));
        } else {
            throw new IllegalArgumentException("Unsupported type");
        }
    }
}