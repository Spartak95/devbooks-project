const pswrd_1 = document.querySelector(".password");
const pswrd_2 = document.querySelector(".confirmPassword");

const showPass = function () {
    if (pswrd_1.type === "password") {
        pswrd_1.type = "text";
    } else {
        pswrd_1.type = "password";
    }
};

const showConfirmPass = function () {
    if (pswrd_2.type === "password") {
        pswrd_2.type = "text";
    } else {
        pswrd_2.type = "password";
    }
};