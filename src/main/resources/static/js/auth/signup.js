import {DateUtils} from "../util/dateUtils.js";
import {Rule, Streamer, StreamerStatus} from "../model/models.js";

class SignupForm {
    form;
    firstName;
    lastName;
    pseudo;
    email;
    birthDate;
    channel;
    cancelBtn;
    submitBtn;
    dateLimit;
    dateLimitError;

    constructor(formId) {
        this.form = document.getElementById(formId);
        this.firstName = document.getElementById("firstName");
        this.lastName = document.getElementById("lastName");
        this.pseudo = document.getElementById("pseudo");
        this.email = document.getElementById("email");
        this.birthDate = document.getElementById("birthDate");
        this.channel = document.getElementById("channel");

        this.cancelBtn = document.getElementById("cancelBtn");
        this.submitBtn = document.getElementById("submitBtn");

        this.submitBtn.disabled = true;

        this.dateLimit = DateUtils.getDateNowByAge(13);
        this.dateLimitError = document.getElementById("dateLimitError");
        this.dateLimitError.textContent = this.dateLimit.getDateFormat_DD_MM_YYYY();

        this.buildForm();
    }

    buildForm() {
        this.buildFormEvent();
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

    validateDateLimit = (input) => {
        const dateUtil = DateUtils.buildDate(input.value);
        if(this.dateLimit.greaterThanOrEquals(dateUtil.date) ){
            input.classList.add("is-valid");
            input.classList.remove("is-invalid");
            return true
        }

        input.classList.add("is-invalid");
        input.classList.remove("is-valid");
        return false;
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

    validateForm = () => {
        this.submitBtn.disabled = Array.of(
            this.validateInputRequired(this.firstName),
            this.validateInputRequired(this.lastName),
            this.validateInputRequired(this.pseudo),
            this.validateEmail(this.email),
            this.validateInputRequired(this.channel),
            this.validateDateLimit(this.birthDate),
        ).includes(false);
    }

    dataStreamerEvent = (streamer) => {
        return new CustomEvent("data-signup-submit", {
            detail : {data: streamer},
            bubbles: true,
            composed: true
        });
    }

    buildFormEvent() {
        this.firstName.addEventListener("keyup", this.validateForm);
        this.lastName.addEventListener("keyup", this.validateForm);
        this.pseudo.addEventListener("keyup", this.validateForm);
        this.email.addEventListener("keyup", this.validateForm);
        this.channel.addEventListener("keyup", this.validateForm);
        this.birthDate.addEventListener("change", this.validateForm);

        this.cancelBtn.addEventListener("click", event => {
            this.clear();
        });

        this.submitBtn.addEventListener("click", event => {
            const streamer = this.extractAndGetData();
            this.form.dispatchEvent(this.dataStreamerEvent(streamer));
        });
    }

    clearValidOrInvalidCSS = (element) => {
        if(element.classList.contains("is-valid")) {
            element.classList.remove("is-valid");
        }
        else {
            element.classList.remove("is-invalid");
        }
    }

    clear = () => {
        this.firstName.value = "";
        this.clearValidOrInvalidCSS(this.firstName);
        this.lastName.value = "";
        this.clearValidOrInvalidCSS(this.lastName);
        this.pseudo.value = "";
        this.clearValidOrInvalidCSS(this.pseudo);
        this.email.value = "";
        this.clearValidOrInvalidCSS(this.email);
        this.channel.value = "";
        this.clearValidOrInvalidCSS(this.channel);
        this.birthDate.value = "";
        this.clearValidOrInvalidCSS(this.birthDate);

        this.submitBtn.disabled = true;

    }

    extractAndGetData = () => {
        const streamer = new Streamer();
        streamer.firstName = this.firstName.value;
        streamer.lastName  = this.lastName.value;
        streamer.pseudo    = this.pseudo.value;
        streamer.email     = this.email.value;
        streamer.birthDate = this.birthDate.value;
        streamer.channel   = this.channel.value;
        return streamer
    }
}


(function() {
    const form = new SignupForm("signupForm");
    form.form.addEventListener("data-signup-submit", event => {
        const streamer = event.detail.data;
        console.log(streamer);
    });
})();