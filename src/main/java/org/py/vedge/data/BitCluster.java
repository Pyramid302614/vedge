package org.py.vedge.data;

public class BitCluster {

    private Bit[] bits;
    private byte[] bytes;

    private final int byteSize = 8;

    public BitCluster() {
        this.bits = new Bit[0];
    }
    public BitCluster(Bit[] bits) {
        this.bits = bits;
    }
    public BitCluster(boolean[] bits) {
        this.bits = new Bit[bits.length];
        for(int i = 0; i < bits.length; i++) this.bits[i] = Bit.make(bits[i]);
    }

    // Optional
    public BitCluster setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }


    public Bit[] getRawContents() {
        return bits;
    }
    public int length() {
        return bits.length;
    }

    public Bit getBit(long index) {
        if(index < bits.length) return bits[(int)index];
        else return Bit.O;
    }
    public int getByte(long index) {
        if(bytes != null) return bytes[(int)index];
        if(index < (int)Math.ceil((double)Math.round((double)bits.length/byteSize*1000.0)/1000)) {
            return getRangeAsInt(index*byteSize,index*byteSize+byteSize);
        } else return 0;
    }

    public void append(Bit... bits) {
        append(bits.length, bits);
    }
    public void append(int minLength, Bit... bits) {
        bytes = null;
        Bit[] newArray = new Bit[this.bits.length+minLength];
        System.arraycopy(bits,0,newArray,this.bits.length+minLength-bits.length,bits.length);
        for(int i = 0; i < minLength-bits.length; i++) newArray[i+this.bits.length] = Bit.O;
        if(this.bits.length != 0) System.arraycopy(this.bits,0,newArray,0,this.bits.length);
        this.bits = newArray;
    }
    // Don't use this lmsao it doesn't even use int index
//    public void insert(int index, Bit... bits) {
//        Bit[] newArray = new Bit[bits.length+this.bits.length];
//        System.arraycopy(bits, 0, newArray, 0, bits.length);
//        System.arraycopy(this.bits, 0, newArray, bits.length, this.bits.length);
//        this.bits = newArray;
//    }

    // Start: inclusive
    // End: exclusive
    public BitCluster getRange(long start, long end) {
        Bit[] bits = new Bit[(int)(end-start)];
        for(int i = 0; i < end-start; i++) {
            bits[i] = getBit(start+i);
        }
        return new BitCluster(bits);
    }
    public int getRangeAsInt(long start, long end) {
        return getRange(start,end).toInteger();
    }
    public String getRangeAsString(long start, long end) {
        return getRange(start,end).toUTF8();
    }

    public int toInteger() {
        int output = 0;
        int length = getRawContents().length;
        for(int i = 0; i < length; i++) {
            if(getBit(i).v()) output += (int)Math.pow(2,length-1-i);
        }
        return output;
    }
    public static BitCluster fromInteger(int integer, long length) {
         Bit[] bits = new Bit[(int)length];
         for(int i = 0; i < length; i++) {
            bits[(int)length-1-i] = Bit.make((integer % 2) == 1);
            integer -= integer % 2;
            integer /= 2;
        }
        return new BitCluster(bits);
    }


    public static BitCluster fromBytes(byte[] bytes) {

        Bit[] output = new Bit[bytes.length*8];

        for(int i = 0; i < bytes.length; i++) {
            for(int j = 0; j < 8; j++) {
                output[i*8+j] = Bit.make((((bytes[i] >> (7 - j)) & 1 ) == 1));
            }
        }

        return new BitCluster(output).setBytes(bytes);

    }
    public byte[] toBytes() {

        if(bytes != null) return bytes;
        else {

            byte[] output = new byte[(int)Math.ceil((double)Math.round((double)bits.length/byteSize*1000.0)/1000)];

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
