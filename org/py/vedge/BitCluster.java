package org.py.vedge;

public class BitCluster {

    private Bit[] bits;
    private byte[] bytes;

    private int byteSize = 8;

    public BitCluster() {
        this.bits = new Bit[0];
    }
    public BitCluster(Bit[] bits) {
        this.bits = bits;
    }
    public BitCluster(boolean[] bits) {
        this.bits = new Bit[bits.length];
        for(int i = 0; i < bits.length; i++) this.bits[i] = new Bit(bits[i]);
    }

    // Optional
    public BitCluster setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }


    public BitCluster setByteSize(int size) {
        byteSize = size;
        return this;
    }

    public Bit[] getRawContents() {
        return bits;
    }
    public int length() {
        return bits.length;
    }

    public Bit getBit(int index) {
        if(index < bits.length) return bits[index];
        else return null;
    }
    public int getByte(int index) {
        if(bytes != null) return bytes[index];
        if(index < bits.length/byteSize) {
            return getRangeAsInt(index*byteSize,index*byteSize+byteSize);
        } else return -1;
    }

    public void append(Bit... bits) {
        append(bits.length, bits);
    }
    public void append(int minLength, Bit... bits) {
        Bit[] newArray = new Bit[this.bits.length+minLength];
        System.arraycopy(bits,0,newArray,this.bits.length+minLength-bits.length,bits.length);
        for(int i = 0; i < minLength-bits.length; i++) newArray[i+this.bits.length] = new Bit(false);
        if(this.bits.length != 0) System.arraycopy(this.bits,0,newArray,0,this.bits.length);
        this.bits = newArray;
    }
    public void insert(int index, Bit... bits) {
        Bit[] newArray = new Bit[bits.length+this.bits.length];
        System.arraycopy(bits, 0, newArray, 0, bits.length);
        System.arraycopy(this.bits, 0, newArray, bits.length, this.bits.length);
        this.bits = newArray;
    }

    // Start: inclusive
    // End: exclusive
    public int getRangeAsInt(int start, int end) {
        Bit[] bits = new Bit[end-start];
        for(int i = 0; i < end-start; i++) {
            bits[i] = getBit(start+i);
        }
        return new BitCluster(bits).toInteger();
    }

    public int toInteger() {
        int output = 0;
        int length = getRawContents().length;
        for(int i = 0; i < length; i++) {
            if(getBit(i).v()) output += (int)Math.pow(2,length-1-i);
        }
        return output;
    }
    public static BitCluster fromInteger(int integer) {
         int length = 0;
         while(Math.pow(2,length) < integer) length++;
         Bit[] bits = new Bit[length];
         for(int i = 0; i < length; i++) {
            bits[length-1-i] = new Bit((integer % 2) == 1);
            integer -= integer % 2;
            integer /= 2;
        }
        return new BitCluster(bits);
    }


    public static BitCluster fromBytes(byte[] bytes) {

        Bit[] output = new Bit[bytes.length*8];

        for(int i = 0; i < bytes.length; i++) {
            for(int j = 0; j < 8; j++) {
                output[i*8+j] = new Bit((((bytes[i] >> (7 - j)) & 1 ) == 1));
            }
        }

        return new BitCluster(output).setBytes(bytes);

    }
    public byte[] toBytes() {

        if(bytes != null) return bytes;
        else {

            byte[] output = new byte[bits.length/8];

            for(int i = 0; i < output.length; i++) {
                output[i] = (byte)getByte(i);
            }

            return output;

        }

    }

    public static BitCluster fromUTF8(String string) {

        return fromBytes(string.getBytes());

    }
    public String toUTF8() {

        return new String(toBytes());

    }


    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(Bit bit : bits) output.append(bit == null ? "n" : ( bit.v() ? "1" : "0" ));
        return output.toString();
    }

}
