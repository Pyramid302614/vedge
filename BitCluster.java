public class BitCluster {

    private final Bit[] bits;
    private byte[] bytes;

    private int byteSize = 8;

    public BitCluster(Bit[] bits) {
        this.bits = bits;
    }

    public BitCluster provideBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }
    public BitCluster setByteSize(int size) {
        byteSize = size;
        return this;
    }

    public Bit[] rawContents() {
        return bits;
    }

    public Bit getBit(int index) {
        if(index < bits.length) return bits[index];
        else return null;
    }
    public byte getByte(int index) {
        if(bytes != null) return bytes[index];
        if(index < bits.length/byteSize) {
            byte output = 0;
            for(int i = 0; i < byteSize; i++) {
                output += (byte)Math.pow(2*(bits[index*8+i].v()?1:0),7-i);
            }
            return output;
        } else return -1;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(Bit bit : bits) output.append(bit == null ? "n" : ( bit.v() ? "1" : "0" ));
        return output.toString();
    }

}
