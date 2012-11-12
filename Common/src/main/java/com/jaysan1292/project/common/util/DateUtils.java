package com.jaysan1292.project.common.util;

import java.util.Date;

public class DateUtils {
    public static final String ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mmZ";

    private DateUtils() {}

    public static String getRelativeDateString(Date value) {
        long delta = ((new Date().getTime() - value.getTime()) / 1000);

        if (delta < 0) {
            return "in the future";
        } else if (delta < 45) {
            return String.format("%d %s ago", delta, (delta != 1) ? "seconds" : "second");
        } else if (delta < 60) {
            return "less than a minute ago";
        } else if (delta < 120) {
            return "about a minute ago";
        } else if (delta < (45 * 60)) {
            long diff = delta / 60;
            return String.format("%d %s ago", diff, (diff != 1) ? "minutes" : "minute");
        } else if (delta < (90 * 60)) {
            return "about an hour ago";
        } else if (delta < (24 * 60 * 60)) {
            long diff = delta / 3600;
            return String.format("%d %s ago", diff, (diff != 1) ? "hours" : "hour");
        } else if (delta < (48 * 60 * 60)) {
            return "1 day ago";
        } else {
            long diff = delta / 86400;
            return String.format("%d %s ago", diff, (diff != 1) ? "days" : "day");
        }
    }
}
