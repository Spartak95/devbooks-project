const progressBar = document.querySelector(".progress-bar");
const progressBarFill = document.querySelector(".progress-bar-fill");
const progressBarText = document.querySelector(".progress-bar-text");
const progressBarCheck = document.querySelector(".progress-bar-check");
const fileInput = document.getElementById("bi");

AWS.config.update({
    credentials: new AWS.CognitoIdentityCredentials({
        IdentityPoolId: 'eu-central-1:226b53dd-e0a5-48fd-9c81-5414fdd2cf1a'
    }),
    region: 'eu-central-1'
});

const bucket = new AWS.S3();

AWS.config.httpOptions.timeout = 0;

const uploadFile = function(fileName, file, folderName) {
    const params = {
        Bucket: "devbooks.am",
        Key: folderName + fileName,
        Body: file,
        ACL: "public-read",
        ContentType: file.type
    };
    return bucket.upload(params, function(err, data) {
        if (err) {
            console.log('There was an error uploading your file: ', err);
            return false;
        }
        console.log('Successfully uploaded file.', data);
        return true;
    });
};

const uploadSampleFile = function() {
    const file = document.getElementById("bi").files[0];
    progressBar.style.display="block";
    const folderName = "pdf/";
    const uniqueFileName = file.name;
    let fileUpload = {
        id: "",
        name: file.name,
        nameUpload: uniqueFileName,
        size: file.size,
        type: "",
        timeReference: 'Unknown',
        progressStatus: 0,
        displayName: file.name,
        status: 'Uploading..',
    };
    uploadFile(uniqueFileName, file, folderName).on('httpUploadProgress', function(progress) {
            let progressPercentage = Math.round(progress.loaded / progress.total * 100);
            console.log(progressPercentage);
            progressBarFill.style.width = progressPercentage + "%";
            progressBarText.textContent = progressPercentage + "%";
            if (progressPercentage < 100) {
                fileUpload.progressStatus = progressPercentage;

            } else if (progressPercentage === 100) {
                fileUpload.progressStatus = progressPercentage;
                progressBarCheck.style.display="block";
                fileUpload.status = "Uploaded";
            }
        })
};

const deleteFile = function() {
    const bucketInstance = new AWS.S3();
    const file = fileInput.files[0];

    progressBar.style.display="none";
    const folderName = "pdf/";
    const uniqueFileName = file.name;

    const params = {
        Bucket: 'devbooks.am',
        Key: folderName + uniqueFileName
    };

    bucketInstance.deleteObject(params, function (err, data) {
        if (data) {
            console.log("File deleted successfully");
        }
        else {
            console.log("Check if you have sufficient permissions : " + err);
        }
    });

    fileInput.value = "";
    progressBarCheck.style.display="none";
};









