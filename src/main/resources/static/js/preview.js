const inpFile = document.querySelector('#bii');
const previewImage = document.querySelector('.book-preview__image');
const previewDefaultText = document.querySelector('.book-preview__text');

inpFile.addEventListener('change', function () {
    const file = this.files[0];

    if (file) {
        const reader = new FileReader();
        previewDefaultText.style.display = 'none';
        previewImage.style.display = 'block';

        reader.addEventListener('load', function () {
            previewImage.setAttribute('src', this.result);
        });

        reader.readAsDataURL(file);
    }
});







