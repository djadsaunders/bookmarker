package com.djad.bookmarker.util;

import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.stereotype.Component;

@Component
public class DatabaseUtils {
    public Blob bytesToBlob(byte[] bytes) throws SQLException, SerialException{
        return new SerialBlob(bytes);
    }

    public byte[] blobToBytes(Blob blob) throws SQLException {
        int blobLength = (int)blob.length();  
        return blob.getBytes(1, blobLength);
    }
}