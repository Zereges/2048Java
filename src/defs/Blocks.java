package defs;

public enum Blocks
{
    BLOCK_0(0, 0),
    BLOCK_2(1, 2),
    BLOCK_4(2, 4),
    BLOCK_8(3, 8),
    BLOCK_16(4, 16),
    BLOCK_32(5, 32),
    BLOCK_64(6, 64),
    BLOCK_128(7, 128),
    BLOCK_256(8, 256),
    BLOCK_512(9, 512),
    BLOCK_1024(10, 1024),
    BLOCK_2048(11, 2048),
    BLOCK_4096(12, 4096),
    BLOCK_8192(13, 8192),
    BLOCK_16384(14, 16384),
    BLOCK_32768(15, 32768),
    BLOCK_65536(16, 65536),
    BLOCK_131072(17, 131072); // Theoretical maximum on 4x4 board.
    
    private final int mId;
    private final int mValue;
    
    Blocks(int id, int value) { mId = id; mValue = value; }
    public final int getId() { return mId; }
    public final int getValue() { return mValue; }
}
