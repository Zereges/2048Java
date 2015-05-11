package defs;

public enum Blocks
{
    BLOCK_0(0),
    BLOCK_2(1),
    BLOCK_4(2),
    BLOCK_8(3),
    BLOCK_16(4),
    BLOCK_32(5),
    BLOCK_64(6),
    BLOCK_128(7),
    BLOCK_256(8),
    BLOCK_512(9),
    BLOCK_1024(10),
    BLOCK_2048(11),
    BLOCK_4096(12),
    BLOCK_8192(13),
    BLOCK_16384(14),
    BLOCK_32768(15),
    BLOCK_65536(16),
    BLOCK_131072(17); // Theoretical maximum on 4x4 board.
    
    private int mValue;
    Blocks(int value)
    {
        mValue = value;
    }
    public int getValue()
    {
        return mValue;
    }
}
