package com.jaysan1292.project.common.data;

import org.apache.commons.lang3.text.WordUtils;

public enum Weekday {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY;

    @Override
    public String toString() {
        return WordUtils.capitalizeFully(super.toString());
    }
}
