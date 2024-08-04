import {AuthService} from "../service/authService.js";
import {ToastUtils} from "../util/toastUtil.js";
import {gotTo} from "../router/router.js";


class SigninForm {
    form;
    email;
    password;
    submitBtn;

    constructor(formId) {
        this.form = document.getElementById(formId);
        this.email = document.getElementById("emailId");
        this.password = document.getElementById("passwordId");

        this.submitBtn = document.getElementById("signinSubmit");
        this.submitBtn.disabled = true;

        this.buildForm();
    }

    buildForm() {
        this.buildFormEvent();
    }

    buildFormEvent() {
        this.email.addEventListener("keyup", this.validateForm);
        this.password.addEventListener("keyup", this.validateForm);

        this.submitBtn.addEventListener("click", event => {
            event.preventDefault();
            const authData = {
                email: this.email.value,
                password : this.password.value
            };
            AuthService.postSignin(authData).then(response => {
                AuthService.setAuthData(response);
                if(AuthService.isAdmin()) {
                    gotTo('/admin');
                }
                else if(AuthService.isStreamer()) {
                    gotTo('/account');
                }
                else {
                    gotTo('/');
                }
            });
        });
    }

    validateForm = () => {
        this.submitBtn.disabled = Array.of(
            this.validateEmail(this.email),
            this.validateInputRequired(this.password),
        ).includes(false);
    }

    validateInputRequired = (input) => {
        if(input.value != ""){
            input.classList.add("is-valid");
            input.classList.remove("is-invalid");
            return true
        }

        input.classList.add("is-invalid");
        input.classList.remove("is-valid");
        return false;
    };

    validateEmail = (email) => {
        if(/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value)) {
            email.classList.add("is-valid");
            email.classList.remove("is-invalid");
            return true;
        }
        email.classList.remove("is-valid");
        email.classList.add("is-invalid");
        return false;
    }
}

class ForgotPasswordForm {
    form;
    email;
    submitBtn;

    constructor(formId) {
        this.form = document.getElementById(formId);
        this.email = document.getElementById("emailFP");

        this.submitBtn = document.getElementById("forgotPasswordSubmit");
        this.submitBtn.disabled = true;

        this.buildForm();
    }

    buildForm() {
        this.buildFormEvent();
    }

    buildFormEvent() {
        this.email.addEventListener("keyup", this.validateForm);

        this.submitBtn.addEventListener("click", event => {
            event.preventDefault();
            const email = this.email.value;
            AuthService.fetchForgotPassword(email).then(response => {
            }).catch(error => { console.error("Error", error)});
        });
    }

    validateForm = () => {
        this.submitBtn.disabled = Array.of(
            this.validateEmail(this.email)
        ).includes(false);
    }


    validateEmail = (email) => {
        if(/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value)) {
            email.classList.add("is-valid");
            email.classList.remove("is-invalid");
            return true;
        }
        email.classList.remove("is-valid");
        email.classList.add("is-invalid");
        return false;
    }
}


(function() {
    const signinForm = new SigninForm("signinForm");
    const forgotPasswordForm = new ForgotPasswordForm("forgotPasswordForm");



})();