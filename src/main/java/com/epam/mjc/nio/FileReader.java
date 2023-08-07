package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;


public class FileReader {

    public Profile getDataFromFile(File file) {
        StringBuilder str = new StringBuilder();
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            FileChannel fileChannel = randomAccessFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());

            while ((fileChannel.read(buffer)) != -1) {
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {
                    str.append((char) buffer.get());
                }
                buffer.clear();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        Map<String, String> map = new HashMap<>();
        String[] keyVals = str.toString().split("[\\r\\n]+");
        for (String keyVal : keyVals) {
            String[] parts = keyVal.split(": ");
            map.put(parts[0], parts[1]);
        }

        return new Profile(map.get("Name"), Integer.parseInt(map.get("Age")), map.get("Email"), Long.valueOf(map.get("Phone")));
    }
}
