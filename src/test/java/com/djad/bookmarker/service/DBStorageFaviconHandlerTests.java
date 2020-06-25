package com.djad.bookmarker.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import com.djad.bookmarker.domain.Favicon;
import com.djad.bookmarker.repository.FaviconRepository;
import com.djad.bookmarker.util.DatabaseUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DBStorageFaviconHandlerTests {

    @InjectMocks
    private DBStorageFaviconHandler handler;

    @Mock
    private FaviconRepository faviconRepository;

    @Mock
    private DatabaseUtils databaseUtils;

    @Test
    public void testWriteFile() throws SQLException {
        String fileName = handler.writeFile(new byte[10]);
        verify(faviconRepository).save(any(Favicon.class));
        assertNotNull(fileName);
    }

    @Test
    public void testWriteFileFails() throws SQLException {    
        when(faviconRepository.save(any())).thenThrow(new RuntimeException());
        String fileName = handler.writeFile(new byte[0]);
        assertNull(fileName);
    }

    @Test
    public void testReadFile() throws SQLException {
        Optional<Favicon> favicon = Optional.of(new Favicon("favicon", new byte[10]));
        when(faviconRepository.findByName(anyString())).thenReturn(favicon);
        assertEquals(10, handler.readFile("myfile").length);
    }

    @Test
    public void testReadFileNoFaviconInDB() throws SQLException {
        Optional<Favicon> favicon = Optional.empty();
        when(faviconRepository.findByName(anyString())).thenReturn(favicon);
        assertNull(handler.readFile("myfile"));
    }
}