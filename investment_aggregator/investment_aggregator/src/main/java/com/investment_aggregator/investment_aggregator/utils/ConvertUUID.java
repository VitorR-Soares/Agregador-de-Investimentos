package com.investment_aggregator.investment_aggregator.utils;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

@Component
public class ConvertUUID {

    public static UUID fromHexStringToUUID(String hex) {
        byte[] bytes = new BigInteger(hex, 16).toByteArray();

        // Garantir que temos 16 bytes
        if (bytes.length > 16) {
            bytes = java.util.Arrays.copyOfRange(bytes, bytes.length - 16, bytes.length);
        } else if (bytes.length < 16) {
            throw new IllegalArgumentException("Invalid UUID binary length");
        }

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        long high = buffer.getLong();
        long low = buffer.getLong();
        return new UUID(high, low);
    }
}
