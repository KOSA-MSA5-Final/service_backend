package com.example.demo.util;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
//import com.google.api.client.auth.oauth2.GoogleCredential;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleVisionOCR {

    @Value("${GOOGLE_ACCESS_TOKEN}")
    private static String accessToken;
    public static String execute(String url) throws IOException {
        StopWatch totalTime = new StopWatch();
        totalTime.start();

        List<AnnotateImageRequest> requests = new ArrayList<>();

        ImageSource imgSource = ImageSource.newBuilder().setImageUri(url).build();
        Image img = Image.newBuilder().setSource(imgSource).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
//            client.setCredentials(GoogleCredential.create(accessToken));
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            StringBuilder result = new StringBuilder();
            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                    return null;
                }

                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    result.append(annotation.getDescription()).append(" ");
                }
            }

            totalTime.stop();
            System.out.println("Total Time : " + totalTime.getTotalTimeMillis() + "ms");

            return result.toString();
        }
        catch (Exception exception) {
            return exception.getMessage();
        }
    }
}
