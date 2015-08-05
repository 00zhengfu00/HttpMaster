package com.jsontojava.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * copy from:org.apache.commons.io.IOUtils;
 * @date 2015/8/5
 */
public class IOUtils {

    public static void write(String data, OutputStream output) throws IOException {
        write(data, output, Charset.defaultCharset());
    }

    public static void write(String data, OutputStream output, Charset encoding) throws IOException {
        if (data != null) {
            output.write(data.getBytes(Charsets.toCharset(encoding)));
        }

    }

    public static String toString(InputStream input) throws IOException {
        return toString(input, Charset.defaultCharset());
    }

    public static String toString(InputStream input, Charset encoding) throws IOException {
        StringBuilderWriter sw = new StringBuilderWriter();
        copy((InputStream) input, (Writer) sw, (Charset) encoding);
        return sw.toString();
    }

    public static void copy(InputStream input, Writer output, Charset encoding) throws IOException {
        InputStreamReader in = new InputStreamReader(input, Charsets.toCharset(encoding));
        copy((Reader) in, (Writer) output);
    }

    public static int copy(Reader input, Writer output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int) count;
    }

    public static long copyLarge(Reader input, Writer output) throws IOException {
        return copyLarge(input, output, new char[4096]);
    }

    public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
        long count = 0L;

        int n1;
        for (boolean n = false; -1 != (n1 = input.read(buffer)); count += (long) n1) {
            output.write(buffer, 0, n1);
        }

        return count;
    }
}
