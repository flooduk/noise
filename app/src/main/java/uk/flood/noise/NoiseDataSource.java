package uk.flood.noise;

import android.media.MediaDataSource;

import java.util.Random;

public class NoiseDataSource extends MediaDataSource {

    private final byte[] x = new byte[]{
            0x52, 0x49, 0x46, 0x46, 0x24, (byte) 0xD3, (byte) 0x8D, 0x71,
            0x57, 0x41, 0x56, 0x45, 0x66, 0x6D, 0x74, 0x20,
            0x10, 0x00, 0x00, 0x00, 0x01, 0x00, 0x01, 0x00,
            0x44, (byte) 0xAC, 0x00, 0x00, (byte) 0x88, 0x58, 0x01, 0x00,
            0x02, 0x00, 0x10, 0x00, 0x64, 0x61, 0x74, 0x61,
            0x00, (byte) 0xD3, (byte) 0x8D, 0x71
    };


    @Override
    public int readAt(long position, byte[] buffer, int offset, int size) {
        new Random().nextBytes(buffer);
        if (position <= x.length) {
            System.arraycopy(x, (int) position, buffer, offset, x.length - (int) position);
        }
        return size;
    }

    @Override
    public long getSize() {
        return 0x718dd324L; // 12 hours
    }

    @Override
    public void close() {

    }

}
