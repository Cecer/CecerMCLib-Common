package com.cecer1.projects.mc.cecermclib.common.modules.logger;

import com.cecer1.projects.mc.cecermclib.common.modules.IModule;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class LoggerModule<TChannel extends LoggerModule.Channel> implements IModule {

    private final ConcurrentMap<Class<?>, TChannel> channelMap;
    private final Function<Class<?>, TChannel> channelConstructor;

    public LoggerModule(Function<Class<?>, TChannel> channelConstructor) {
        this.channelMap = new ConcurrentHashMap<>();
        this.channelConstructor = channelConstructor;
    }

    @SuppressWarnings("rawtypes")
    public TChannel getChannel(Class clazz) {
        return this.channelMap.computeIfAbsent(clazz, this.channelConstructor);
    }


    public static class Channel {

        private final String name;
        public String getName() {
            return this.name;
        }

        public Channel(Class<?> clazz) {
            this.name = clazz.getSimpleName();
        }

        public void log(String message, Object... args) {
            LogEntry entry = new LogEntry(System.currentTimeMillis(), this, String.format(message, args));
            this.process(entry);
        }
        protected void process(LogEntry entry) {
            String timestamp = Instant.ofEpochMilli(entry.getTimestamp()).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
            System.out.printf("[%s] [%s] %s%n", timestamp, entry.getChannel().getName(), entry.getMessage());
        }
    }

    public static class LogEntry {
        private final long timestamp;
        private final Channel channel;
        private final String message;

        public long getTimestamp() {
            return this.timestamp;
        }
        public Channel getChannel() {
            return this.channel;
        }
        public String getMessage() {
            return this.message;
        }

        public LogEntry(long timestamp, Channel channel, String message) {
            this.timestamp = timestamp;
            this.channel = channel;
            this.message = message;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LogEntry logEntry = (LogEntry) o;
            return getTimestamp() == logEntry.getTimestamp() &&
                    Objects.equals(getChannel(), logEntry.getChannel()) &&
                    Objects.equals(getMessage(), logEntry.getMessage());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTimestamp(), getChannel(), getMessage());
        }
    }
}
