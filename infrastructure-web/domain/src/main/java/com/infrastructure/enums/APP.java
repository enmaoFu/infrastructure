package com.infrastructure.enums;

import org.springframework.util.StringUtils;

/**
 * APP平台
 *
 * @author tyq
 * @data 2016/1/14
 */
public enum APP implements IDescription {
    IOS("IOS"), // iOS
    Android("Android"); // Android

    private final String description;

    APP(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static APP get(String deviceType) {
        if (!StringUtils.hasText(deviceType))
            return null;

        for (APP app : APP.values()) {
            if (app.getDescription().toLowerCase().equals(deviceType.toLowerCase()))
                return app;
        }
        return null;
    }
}
