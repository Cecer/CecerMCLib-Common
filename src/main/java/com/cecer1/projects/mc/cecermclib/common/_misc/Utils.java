package com.cecer1.projects.mc.cecermclib.common._misc;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class Utils {

    public static int clamp(int value, int minValue, int maxValue) {
        return Math.max(minValue, Math.min(maxValue, value));
    }
    public static long clamp(long value, long minValue, long maxValue) {
        return Math.max(minValue, Math.min(maxValue, value));
    }
    public static float clamp(float value, float minValue, float maxValue) {
        return Math.max(minValue, Math.min(maxValue, value));
    }
    public static double clamp(double value, double minValue, double maxValue) {
        return Math.max(minValue, Math.min(maxValue, value));
    }


    public static float absClamp(float value, float minValue, float maxValue) {
        return clamp(Math.abs(value), minValue, maxValue) * Math.signum(value);
    }
    public static double absClamp(double value, double minValue, double maxValue) {
        return clamp(Math.abs(value), minValue, maxValue) * Math.signum(value);
    }

    public static void openLink(URI link) {
        try {
            java.awt.Desktop.getDesktop().browse(link);
        } catch (IOException e) {
            System.err.println("Couldn't open link");
            e.printStackTrace();
        }
    }

    public static <T> CompletableFuture<T> failedFuture(Throwable e) {
        CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(e);
        return future;
    }
    public static Throwable unwrapException(CompletionException e) {
        Throwable t = e;
        while (t.getCause() != t && t instanceof CompletionException) {
            t = t.getCause();
        }
        return t;
    }
}
