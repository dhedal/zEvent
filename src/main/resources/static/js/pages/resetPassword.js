import {AuthService} from "../service/authService.js";

class ResetPasswordForm {
    form;
    password;
    passwordCopy;
    charLengthError;
    charUpperLowerError;
    charNumberError;
    charSpecialError;
    submitBtn;

    constructor(formId) {
        this.form = document.getElementById(formId);
        this.password = document.getElementById("password");
        this.password.value = "";
        this.passwordCopy = document.getElementById("passwordCopy");

        this.charLengthError = document.getElementById("charLengthError");
        this.charUpperLowerError = document.getElementById("charUpperLowerError");
        this.charNumberError = document.getElementById("charNumberError");
        this.charSpecialError = document.getElementById("charSpecialError");

        this.submitBtn = document.getElementById("submitBtn");
        this.submitBtn.disabled = true;

        this.buildFormEvent();
    }


    buildFormEvent() {
        this.password.addEventListener("keyup", () => this.validateForm());
        this.passwordCopy.addEventListener("keyup", () => this.validateForm());

        this.submitBtn.addEventListener("click", event => {
            event.preventDefault();
            console.log(event);
            const resetPassword = {token : "", password : this.password.value.trim()};
            const customEvent = new CustomEvent("reset-password-valid", {
                detail : {data: resetPassword},
                bubbles: true,
                composed: true
            });
            this.form.dispatchEvent(customEvent);
        });
    }

    validatePasswordSize(string, error){
        if(!(string == null || string === "" || string.length < 8)) {
            error.classList.add("is-valid");
            error.classList.remove("is-invalid");
            return true;
        }

        error.classList.remove("is-valid");
        error.classList.add("is-invalid");
        return false;

    }

    validatePasswordContainsUpperAndLowerCase(string, error){
        if(/[A-Z]/.test(string) && /[a-z]/.test(string)) {
            error.classList.add("is-valid");
            error.classList.remove("is-invalid");
            return true;
        }

        error.classList.remove("is-valid");
        error.classList.add("is-invalid");
        return false;
    }

    validatePasswordContainsCharNumber(string, error){
        if(/[0-9]/.test(string)) {
            error.classList.add("is-valid");
            error.classList.remove("is-invalid");
            return true;
        }

        error.classList.remove("is-valid");
        error.classList.add("is-invalid");
        return false;
    }

    validatePasswordContainsSpecialCharacters(string, error){
        if(/[!@#$%&*?:+-]/.test(string)) {
            error.classList.add("is-valid");
            error.classList.remove("is-invalid");
            return true;
        }

        error.classList.remove("is-valid");
        error.classList.add("is-invalid");
        return false;
    }

    validatePassWord(password){
        const value = password.value;
        const check = !Array.of(
            this.validatePasswordSize(value, this.charLengthError),
            this.validatePasswordContainsUpperAndLowerCase(value, this.charUpperLowerError),
            this.validatePasswordContainsCharNumber(value, this.charNumberError),
            this.validatePasswordContainsSpecialCharacters(value, this.charSpecialError)
        ).includes(false);

        if(check) {
            password.classList.add("is-valid");
            password.classList.remove("is-invalid");
        }
        else {
            password.classList.remove("is-valid");
            password.classList.add("is-invalid");
        }
        return check;
    }

    validatePasswordEquals(password, passwordValue) {
        if(password.value === passwordValue) {
            password.classList.add("is-valid");
            password.classList.remove("is-invalid");
            return true;
        }

        password.classList.remove("is-valid");
        password.classList.add("is-invalid");
        return false;
    }

    validateForm() {
        this.submitBtn.disabled = Array.of(
            this.validatePassWord(this.password),
            this.validatePasswordEquals(this.passwordCopy, this.password.value)
        ).includes(false);
    }

    clear = () => {

        this.password.value = "";
        this.clearValidOrInvalidCSS(this.password);
        this.passwordCopy.value = "";
        this.clearValidOrInvalidCSS(this.passwordCopy);

        this.clearValidOrInvalidCSS(this.charLengthError);
        this.clearValidOrInvalidCSS(this.charUpperLowerError);
        this.clearValidOrInvalidCSS(this.charNumberError);
        this.clearValidOrInvalidCSS(this.charSpecialError);

        this.submitBtn.disabled = true;

    }

    clearValidOrInvalidCSS = (element) => {
        if(element.classList.contains("is-valid")) {
            element.classList.remove("is-valid");
        }
        else {
            element.classList.remove("is-invalid");
        }
    }
}

const invalidUrlMsg = () => {
    const message = document.getElementById("message");
    message.innerHTML = `
    <p>
        <span class="fs-3">L'url n'est plus valide</span>.<br/>
        <a class="link-info" href="/signin">Réinitaliser votre mot de passe</a>
    </p>
    `;
}

const successMsg = (formContent) => {
    formContent.classList.remove("visible");
    formContent.classList.add("invisible");
    const message = document.getElementById("message");
    message.innerHTML = `
    <p>
        <span class="fs-3">Votre mot de passe a été réinitialisé</span>.<br/>
        <a class="link-info" href="/signin">Connecter vous</a>
    </p>
    `;
};

const echecMsg = (formContent) => {
    formContent.classList.remove("visible");
    formContent.classList.add("invisible");
    const message = document.getElementById("message");
    message.innerHTML = `
    <p>
        <span class="fs-3">Un problème est survenu</span>.<br/>
        <a class="link-info" href="/signin">Réinitialiser votre mot de passe</a>
    </p>
    `;
};

(function() {
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get("token");
    if(token == null) {
        invalidUrlMsg();
    }
    else {
        const formContent = document.getElementById("formContent");
        formContent.classList.remove("invisible");
        formContent.classList.add("visible");
        const form = new ResetPasswordForm("resetPasswordForm");
        form.form.addEventListener("reset-password-valid", event => {
            event.preventDefault();
            const data = event.detail.data;
            data.token = token;
            AuthService.postRestPassword(data).then(response => {
                console.log(response);
                form.clear();
                if(response == true) successMsg(formContent);
                else echecMsg(formContent);
            });
        });
    }

})();