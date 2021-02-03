const inpFile = document.querySelector('#bii');
const previewImage = document.querySelector('#preImage');

inpFile.addEventListener('change', function () {
    const file = this.files[0];

    if (file) {
        const reader = new FileReader();
        reader.addEventListener('load', function () {
            previewImage.setAttribute('src', this.result);
        });

        reader.readAsDataURL(file);
    }
});