package com.djad.bookmarker.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import org.springframework.stereotype.Component;

import lombok.Cleanup;

@Component
public class AmazonS3Utils {

    public byte[] getObjectAsByteArray(String bucketName, String keyName) throws IOException {
        byte[] result = null;

        AmazonS3 client = this.getClient();
        S3Object s3Obj = client.getObject(bucketName, keyName);
        
        @Cleanup S3ObjectInputStream s3is = s3Obj.getObjectContent();
        result = IOUtils.toByteArray(s3is);
        
        return result;
    }

    public void putObject(String bucketName, String keyName, byte[] contents) throws IOException {

        final AmazonS3 client = this.getClient();

        // Process writes a file to upload then deletes in when done
        // Maybe there is a better way to do this...

        File file = new File(keyName);

        try {        
            @Cleanup FileOutputStream fos = new FileOutputStream(file);
            fos.write(contents);
            client.putObject(bucketName, keyName, file);
        }
        finally {
            file.delete();
        }
    }

    private AmazonS3 getClient() {
        return AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();
    }
}