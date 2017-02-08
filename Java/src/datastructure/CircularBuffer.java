package datastructure;

/**
 * Created by mazhuang on 2017/2/8.
 */
public class CircularBuffer {
    private int mSize = 0;
    private int mWritePos = 0;
    private int mReadPos = 0;
    private byte[] mBuffer;

    public CircularBuffer(int bufSize) {
        if (!isPowerOf2(bufSize)) {
            bufSize = roundUpPowOf2(bufSize);
        }

        mSize = bufSize;
        mBuffer = new byte[bufSize];
    }

    public int enqData(byte[] data, int len, int offset) {
        int remainLen;
        len = Math.min(len, mSize - mWritePos + mReadPos);
        remainLen = Math.min(len, mSize - (mWritePos & (mSize - 1)));
        System.arraycopy(data, offset, mBuffer, (mWritePos & (mSize - 1)), remainLen);
        System.arraycopy(data, offset + remainLen, mBuffer, 0, len - remainLen);

        mWritePos += len;

        return len;
    }

    public int deqData(byte[] buffer, int len, int offset) {
        int remainLen;

        len = Math.min(len, mWritePos - mReadPos);
        remainLen = Math.min(len, mSize - (mReadPos & (mSize - 1)));
        System.arraycopy(mBuffer, (mReadPos & (mSize - 1)), buffer, offset, remainLen);
        System.arraycopy(mBuffer, 0, buffer, offset + remainLen, len - remainLen);

        mReadPos += len;

        return len;
    }

    private boolean isPowerOf2(int val) {
        return ((val > 0) && ((val & (val - 1)) == 0));
    }

    private int roundUpPowOf2(int val) {
        int ret = 2;
        while (ret < val) {
            ret *= 2;
        }

        return ret;
    }
}
