package sandbox;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void truncateByByteLength() throws Exception {
        String input = "あいうえお123";
        {
            String result = StringUtils.truncateByByteLength(input, 9, "UTF-8");
            assertEquals("", "あいう", result);
        }
        {
            String result = StringUtils.truncateByByteLength(input, 10, "UTF-8");
            assertEquals("", "あいう", result);
        }
        {
            String result = StringUtils.truncateByByteLength(input, 15, "UTF-8");
            assertEquals("", "あいうえお", result);
        }
        {
            String result = StringUtils.truncateByByteLength(input, 16, "UTF-8");
            assertEquals("", "あいうえお1", result);
        }
    }
}
