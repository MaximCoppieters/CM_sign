package be.pxl.business;

import org.apache.http.HttpEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EntityUtility {

    public static void printEntity(HttpEntity entity) {
        ByteArrayOutputStream printOutPutStream = new ByteArrayOutputStream();
        try {
            entity.writeTo(printOutPutStream);
            System.out.println(printOutPutStream.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
