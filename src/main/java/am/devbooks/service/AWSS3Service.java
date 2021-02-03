package am.devbooks.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class AWSS3Service {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.endpointUrl}")
    private String endpointUrl;

    @Autowired
    public AWSS3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadImage(MultipartFile multipartFile, boolean enablePublicReadAccess) {
        String fileUrl = "";

        try {
            File file = convertMultiPartFileToFile(multipartFile);
            String fileName = multipartFile.getOriginalFilename();
            fileUrl = endpointUrl + "/" + bucketName + "/img/" + fileName;
            uploadImageToS3Bucket(bucketName, file, enablePublicReadAccess);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileUrl;
    }

    public String uploadPDF(MultipartFile multipartFile, boolean enablePublicReadAccess) {
        String fileName = multipartFile.getOriginalFilename();
        String fileUrl = endpointUrl + "/" + bucketName + "/pdf/" + fileName;

//        String fileUrl = "";
//
//        try {
//            File file = convertMultiPartFileToFile(multipartFile);
//            String fileName = multipartFile.getOriginalFilename();
//            fileUrl = endpointUrl + "/" + bucketName + "/pdf/" + fileName;
//            uploadPDFToS3Bucket(bucketName, file, enablePublicReadAccess);
//            file.delete();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return fileUrl;
    }

    private File convertMultiPartFileToFile(MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }

    private void uploadImageToS3Bucket(String bucketName, File file, boolean enablePublicReadAccess) {
        String fileName = "img/" + file.getName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        if (enablePublicReadAccess) {
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        }
        amazonS3.putObject(putObjectRequest);
    }

    private void uploadPDFToS3Bucket(String bucketName, File file, boolean enablePublicReadAccess) throws InterruptedException {
        String fileName = "pdf/" + file.getName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);

        if (enablePublicReadAccess) {
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        }

        amazonS3.putObject(putObjectRequest);
    }

    public void deleteFile(String keyName) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, keyName);
        amazonS3.deleteObject(deleteObjectRequest);
    }
}

