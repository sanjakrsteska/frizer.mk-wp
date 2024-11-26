function validateReviewForm() {
    const ratingInput = document.querySelector(".reviewAddForm #rating")

    if (ratingInput) {
        const ratingValue = parseFloat(ratingInput.value);
        if (!isNaN(ratingValue) && ratingValue >= 0 && ratingValue <= 5) {
            return true;
        }
    }
    alert("Оценката мора да биде помеѓу 0 и 5")
    return false;
}

function validateRegisterForm() {
    const password = document.querySelector("#registerForm #password").value;
    const repeatedPassword = document.querySelector("#registerForm #repeatedPassword").value;
    const phoneNumberInput = document.querySelector("#registerForm #phoneNumber").value;

    const isPhoneOk = isPhoneOk(phoneNumberInput);

    if (!isPhoneOk) {
        alert("Телефонскиот број мора да е 9 карактери и да почнува со 07 или +3897.");
        return false;
    }

    if (!isPasswordOk(password)) {
        alert("Лозинката мора да има барем 8 карактери, една голема буква, една мала буква, еден број и еден специјален карактер.");
        return false;
    }

    if (password !== repeatedPassword) {
        alert("Лозинките не се совпаѓаат.");
        return false;
    }

    return true;
}

function validateProfileEditForm() {
    const firstName = document.querySelector("#profileEditForm #firstName").value;
    const lastName = document.querySelector("#profileEditForm #lastName").value;
    const phoneNumber = document.querySelector("#profileEditForm #phoneNumber").value;

    firstNameOk = isNameOk(firstName)
    if (!firstNameOk) {
        alert("Вашето име треба да не е празно и да содржи само букви.");
        return false;
    }
    lastNameOk = isNameOk(lastName)
    if (!lastNameOk) {
        alert("Вашето презиме треба да не е празно и да содржи само букви.");
        return false;
    }
    phoneNumberOk = isPhoneOk(phoneNumber)
    if (!phoneNumberOk) {
        alert("Телефонскиот број мора да е 9 карактери и да почнува со 07 или +3897.");
        return false;
    }

    return true;
}

function validateSalonAddForm() {
    const name = document.querySelector("#salonAddForm #name").value;
    const description = document.querySelector("#salonAddForm #description").value;
    const location = document.querySelector("#salonAddForm #location").value;
    const phoneNumber = document.querySelector("#salonAddForm #phoneNumber").value;
    const latitude = document.querySelector("#salonAddForm #latitude").value;
    const longitude = document.querySelector("#salonAddForm #longitude").value;

    if (!isNameOk(name)) {
        alert("Името треба да содржи само букви и да не биде празно.")
        return false;
    }

    if (location.length === 0) {
        alert("Локацијата не треба да биде празна.")
        return false;
    }

    if (!isPhoneOk(phoneNumber)) {
        alert("Телефонскиот број мора да е 9 карактери и да почнува со 07 или +3897.");
        return false;
    }

    if (!isCoordinateOk(latitude, "lat") || !isCoordinateOk(longitude, "lon")) {
        alert("Географската ширина или должина не се во ред.")
        return false;
    }

    return true;
}

function confirmDelete(type) {
    return confirm(`Дали си сигурен дека сакаш да го откажеш овој ${type}?`);
}

function confirmDone() {
    return confirm("Дали си сигурен дека овој термин е завршен?");
}

function isNameOk(name) {
    const hasOnlyLettersAndSpaces = /^[A-Za-zА-Яа-яЁё\u0400-\u04FF\u0500-\u052F\s]+$/.test(name);
    return name !== null && name.length > 0 && hasOnlyLettersAndSpaces;
}

function isPhoneOk(phone) {
    return (phone.length === 9 && phone.startsWith("07")) ||
        (phone.length === 12 && phone.startsWith("+3897"));
}

function isPasswordOk(password) {
    const minLength = 8;
    const hasUpperCase = /[A-Z]/.test(password);
    const hasLowerCase = /[a-z]/.test(password);
    const hasNumber = /[0-9]/.test(password);
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

    return password.length >= minLength && hasUpperCase && hasLowerCase && hasNumber && hasSpecialChar;
}

function isCoordinateOk(coordinate, type) {
    //40.873926 - 42.376477 latitude
    //20.453475 - 23.040348 longitude
    const isValidCoordinate = /^-?\d+(\.\d+)?$/.test(coordinate);
    if (isValidCoordinate) {
        let isTypeGood = false;
        const value = parseFloat(coordinate);

        if (type === "lat") {
            isTypeGood = value >= 40.873926 && value <= 42.376477;
        } else if (type === "lon") {
            isTypeGood = value >= 20.453475 && value <= 23.040348;
        }

        return isTypeGood;
    }
    return false;
}

function validatePasswordUpdateForm() {
    oldPassword = document.querySelector('#passwordEditForm #oldPassword').value
    newPassword = document.querySelector('#passwordEditForm #newPassword').value
    repeatedPassword = document.querySelector('#passwordEditForm #repeatedPassword').value

    if (!isPasswordOk(newPassword)) {
        alert("Лозинката мора да има барем 8 карактери, една голема буква, една мала буква, еден број и еден специјален карактер.");
        return false;
    }
    if (newPassword !== repeatedPassword) {
        alert("Лозинките не се совпаѓаат.");
        return false;
    }

    return true;
}