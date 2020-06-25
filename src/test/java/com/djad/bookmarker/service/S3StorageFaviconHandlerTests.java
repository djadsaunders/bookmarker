package com.djad.bookmarker.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.djad.bookmarker.util.AmazonS3Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class S3StorageFaviconHandlerTests {

    @InjectMocks
    private S3StorageFaviconHandler handler;

    @Mock
    private AmazonS3Utils s3Utils;

    @Test
    public void testWriteFile() {
        String fileName = handler.writeFile(new byte[0]);
        assertNotNull(fileName);
    }

    @Test
    public void testWriteFileFails() throws IOException {        
        doThrow(new IOException()).when(s3Utils).putObject(anyString(), anyString(), any());
        String fileName = handler.writeFile(new byte[0]);
        assertNull(fileName);
    }

    @Test
    public void testReadFile() throws IOException {
        when(s3Utils.getObjectAsByteArray(anyString(), anyString())).thenReturn(new byte[10]);
        byte[] result = handler.readFile("myfile");
        assertEquals(10, result.length);
    }

    @Test
    public void testReadFileFails() throws IOException {
        when(s3Utils.getObjectAsByteArray(anyString(), anyString())).thenThrow(new IOException());
        assertNull(handler.readFile("myfile"));
    }
}