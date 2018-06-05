import com.company.Compressor;
import com.company.Decompressor;
import com.company.FileUtils;
import com.company.UnexpectedFileFormat;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.*;

public class CompressorTest {
    @Test
    public void testCompressString() {
        String fileName = "text.txt";
        byte[] actual = FileUtils.readBytes(fileName);
        byte[] compressed = Compressor.instance().compress(actual);
        assertTrue(compressed.length < actual.length);
    }

    @Test
    public void testSplitStringToByte() {
        String str = "01011101";
        byte[] actual = Compressor.instance().splitStringToByte(str);
        byte[] expected = {93};
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
