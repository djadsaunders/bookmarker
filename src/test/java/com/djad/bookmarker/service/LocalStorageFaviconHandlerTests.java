package com.djad.bookmarker.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.djad.bookmarker.util.FileUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalStorageFaviconHandlerTests {

    @InjectMocks
    private LocalStorageFaviconHandler handler;

    @Mock
    private FileUtils fileUtils;

    @Test
    public void testWriteFile() {
        String fileName = handler.writeFile(new byte[0]);
        assertNotNull(fileName);
    }

    @Test
    public void testWriteFileFails() throws IOException {        
        doThrow(new IOException()).when(fileUtils).writeFile(anyString(), any());
        String fileName = handler.writeFile(new byte[0]);
        assertNull(fileName);
    }

    @Test
    public void testReadFile() throws IOException {
        when(fileUtils.readFileAsByteArray(anyString())).thenReturn(new byte[10]);
        byte[] result = handler.readFile("myfile");
        assertEquals(10, result.length);
    }

    @Test
    public void testReadFileFails() throws IOException {
        when(fileUtils.readFileAsByteArray(anyString())).thenThrow(new IOException());
        assertNull(handler.readFile("myfile"));
    }
}