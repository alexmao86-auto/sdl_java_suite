package com.smartdevicelink.managers.lockscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smartdevicelink.util.DebugTool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class LockScreenDeviceIconManager {

    private Context context;
    private static final String SDL_DEVICE_STATUS_SHARED_PREFS = "sdl.lockScreenIcon";
    private static final String STORED_ICON_PATH = "sdl/lock_screen_icon/";

    LockScreenDeviceIconManager(Context context) {
        this.context = context;
        File lockScreenDirectory = new File(context.getCacheDir(), STORED_ICON_PATH);
        lockScreenDirectory.mkdirs();
    }

    boolean isIconCachedAndValid(String iconUrl) {
        String iconHash = getMD5HashFromIconUrl(iconUrl);
        SharedPreferences sharedPref = this.context.getSharedPreferences(SDL_DEVICE_STATUS_SHARED_PREFS, Context.MODE_PRIVATE);
        String iconLastUpdatedTime = sharedPref.getString(iconHash, null);
        if(iconLastUpdatedTime == null) {
            DebugTool.logInfo("No Icon Details Found In Shared Preferences");
            return false;
        } else {
            DebugTool.logInfo("Icon Details Found");
            long lastUpdatedTime = 0;
            try {
                lastUpdatedTime = Long.parseLong(iconLastUpdatedTime);
            } catch (NumberFormatException e) {
                DebugTool.logInfo("Invalid time stamp stored to shared preferences, clearing cache and share preferences");
                clearIconDirectory();
                sharedPref.edit().remove(iconHash).commit();
            }
            long currentTime = System.currentTimeMillis();

            long timeDifference = currentTime - lastUpdatedTime;
            long daysBetweenLastUpdate = timeDifference / (1000 * 60 * 60 * 24);
            return daysBetweenLastUpdate < 30;
        }
    }

    void saveFileToCache(Bitmap icon, String iconUrl) {
        String iconHash = getMD5HashFromIconUrl(iconUrl);
        File f = new File(this.context.getCacheDir() + "/" + STORED_ICON_PATH, iconHash);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapData = bos.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            DebugTool.logError("Failed to save icon to cache");
            e.printStackTrace();
            return;
        }

        writeDeviceIconParametersToSharedPreferences(iconHash);
    }

    Bitmap getFileFromCache(String iconUrl) {
        String iconHash = getMD5HashFromIconUrl(iconUrl);
        SharedPreferences sharedPref = this.context.getSharedPreferences(SDL_DEVICE_STATUS_SHARED_PREFS, Context.MODE_PRIVATE);
        String iconLastUpdatedTime = sharedPref.getString(iconHash, null);

        if (iconLastUpdatedTime != null) {
            Bitmap cachedIcon = BitmapFactory.decodeFile(this.context.getCacheDir() + "/" + STORED_ICON_PATH + "/" + iconHash);
            if(cachedIcon == null) {
                DebugTool.logError("Failed to get Bitmap from decoding file cache");
                clearIconDirectory();
                sharedPref.edit().remove(iconHash).commit();
                return null;
            } else {
                return cachedIcon;
            }
        } else {
            DebugTool.logError("Failed to get system preferences");
            return null;
        }
    }

    private void writeDeviceIconParametersToSharedPreferences(String iconHash) {
        SharedPreferences sharedPref = this.context.getSharedPreferences(SDL_DEVICE_STATUS_SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(iconHash, String.valueOf(System.currentTimeMillis()));
        editor.commit();
    }

    String getMD5HashFromIconUrl(String iconUrl) {
        String iconHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(iconUrl.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            iconHash = hashtext;
        } catch (NoSuchAlgorithmException e) {
            DebugTool.logError("Unable to hash icon url");
            e.printStackTrace();
        }
        return iconHash;
    }

    private void clearIconDirectory() {
        File iconDir = new File(context.getCacheDir() + "/" + STORED_ICON_PATH);
        if (iconDir.listFiles() != null) {
            for (File child : iconDir.listFiles()) {
                child.delete();
            }
        }
    }
}
