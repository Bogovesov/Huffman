import com.company.*;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.*;

public class CompressorTest {
    @Test
    public void testIsCompressFile() {
        String fileName = "text.txt";
        byte[] actual = FileUtils.readBytes(fileName);
        byte[] compressed = Compressor.instance().compress(actual);
        assertTrue(compressed.length < actual.length);
    }

    @Test
    public void testSplitStringToByte() {
        String str = "01011101";
        byte[] actual = Splitter.toBytes(str);
        byte[] expected = {Byte.parseByte(str, 2)};
        assertEquals(actual[0], expected[0]);
    }

    @Test
    public void testCompressDecompress() throws UnexpectedFileFormat {
        String fileName = "text.txt";
        byte[] expected = FileUtils.readBytes(fileName);
        Compressor.instance().compress(fileName);
        byte[] actual = Decompressor.instance().decompress(fileName + FileUtils.EXT_COMPRESSED);
        assertTrue(Arrays.equals(actual, expected));
    }
}
