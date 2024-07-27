import {Rule, Streamer, StreamerStatus} from "../model/models.js";
import {DateUtils} from "../util/dateUtils.js";

export class StreamerTable{
    parent;
    table;
    thead;
    streamerMap;
    tbody;
    constructor(parentId, streamerArray = new Array()) {
        this.parent = document.getElementById(parentId);
        this.table = document.createElement("table");
        this.parent.innerHTML = "";
        this.parent.appendChild(this.table);
        this.table.classList.add("table", "table-striped",  "table-sm");

        this.thead = this.createAndGetTHead();
        this.table.appendChild(this.thead);

        this.tbody = document.createElement("tbody");
        this.table.appendChild(this.tbody);

        this.streamerMap = new Map();
        this.addStreamerArray(streamerArray);

    }

    getStreamerByUUID = (uuid) => {
        return this.streamerMap.get(uuid);
    }

    createAndGetTHead() {
        const thead = document.createElement("thead");
        thead.innerHTML = `
        <tr>
            <th scope="col">#</th>
            <th scope="col">Nom</th>
            <th scope="col">Prénom</th>
            <th scope="col">Pseudo</th>
            <th scope="col">Email</th>
            <th scope="col">Date de naissance</th>
            <th scope="col">Chaine</th>
            <th scope="col">Role</th>
            <th scope="col">Status</th>
            <th scope="col">Modifier</th>
        </tr>
        `;
        return thead;
    }

    createAndGetTd(data) {
        const td = document.createElement("td");
        td.textContent = data;
        return td;
    }

    createAndGetTdCheckbox(streamerUUID) {
        const td = document.createElement("td");
        const input = document.createElement("input");
        input.setAttribute("type", "checkbox");
        td.appendChild(input);

        input.addEventListener("change", event => {
            const btn = document.querySelector(`button[data-uuid="${streamerUUID}"]`);
            if(btn) btn.disabled = !input.checked;
        })
        return td;
    }

    createAndGetTdButton(streamerUuid) {
        const td = document.createElement("td");
        const btn = document.createElement("button");
        btn.setAttribute("data-uuid", streamerUuid);
        btn.setAttribute("data-bs-toggle", "modal");
        btn.setAttribute("data-bs-target", "#streamerModal");
        btn.setAttribute("data-bs-whatever", "Modifier");

        btn.disabled = true;
        btn.classList.add("btn", "btn-outline-warning");
        btn.textContent = "Modifier";
        td.appendChild(btn);

        btn.addEventListener("click", event => {
            console.log(this.streamerMap.get(streamerUuid));
        });
        return td;
    }

    createAndGetTr(streamer) {
        const tr = document.createElement("tr");
        tr.setAttribute("id", streamer.uuid);
        this.fillLine(tr, streamer);
        return tr;
    }

    fillLine(tr, streamer) {
        tr.appendChild(this.createAndGetTdCheckbox(streamer.uuid));
        tr.appendChild(this.createAndGetTd(streamer.firstName));
        tr.appendChild(this.createAndGetTd(streamer.lastName));
        tr.appendChild(this.createAndGetTd(streamer.pseudo));
        tr.appendChild(this.createAndGetTd(streamer.email));
        tr.appendChild(this.createAndGetTd(streamer.birthDate));
        tr.appendChild(this.createAndGetTd(streamer.channel));
        tr.appendChild(this.createAndGetTd(streamer.rule.label));
        tr.appendChild(this.createAndGetTd(streamer.status.label));
        tr.appendChild(this.createAndGetTdButton(streamer.uuid));
    }

    clear(){
        this.tbody.innerHTML = "";
        this.streamerMap.clear();
    }

    addStreamer(streamer) {
        if(streamer.uuid == null || streamer.uuid.length == 0) return;
        if(this.streamerMap.has(streamer.uuid)) {
            const tr = document.getElementById(streamer.uuid);
            tr.innerHTML = "";
            this.fillLine(tr, streamer);
        }
        else {
            this.tbody.appendChild(this.createAndGetTr(streamer));
        }
        this.streamerMap.set(streamer.uuid, streamer);
    }

    addStreamerArray(streamerArray) {
        this.clear();
        streamerArray.forEach((streamer) => {
            this.addStreamer(streamer);
        });
    }

}

export class StreamerForm {
    form;
    firstName;
    lastName;
    pseudo;
    email;
    birthDate;
    channel;
    rule;
    status;
    // password;
    // password2;
    // charLengthError;
    // charUpperLowerError;
    // charNumberError;
    // charSpecialError;
    cancelBtn;
    submitBtn;
    dataStatusMap;
    dataRuleMap;
    dateLimit;
    dateLimitError;

    streamer;

    constructor(streamerForm) {
        this.form = document.getElementById("streamerForm");
        this.firstName = document.getElementById("firstName");
        this.lastName = document.getElementById("lastName");
        this.pseudo = document.getElementById("pseudo");
        this.email = document.getElementById("email");
        this.birthDate = document.getElementById("birthDate");
        this.channel = document.getElementById("channel");
        this.rule = document.getElementById("rule");
        this.status = document.getElementById("status");
        // this.password = document.getElementById("passwordId");
        // this.password2 = document.getElementById("password2");
        //
        // this.charLengthError = document.getElementById("charLengthError");
        // this.charUpperLowerError = document.getElementById("charUpperLowerError");
        // this.charNumberError = document.getElementById("charNumberError");
        // this.charSpecialError = document.getElementById("charSpecialError");

        this.cancelBtn = document.getElementById("streamerFormCancelBtn");
        this.submitBtn = document.getElementById("streamerFormSubmitBtn");

        this.dataRuleMap = new Map();
        this.dataStatusMap = new Map();

        this.submitBtn.disabled = true;

        this.dateLimit = DateUtils.getDateNowByAge(13);
        this.dateLimitError = document.getElementById("dateLimitError");
        this.dateLimitError.textContent = this.dateLimit.getDateFormat_DD_MM_YYYY();

        // this.password.value = "";

        this.streamer = null;
    }

    buildForm(ruleList, statusList) {
        ruleList.forEach(ruleItem => {
            this.dataRuleMap.set(ruleItem.key+"", ruleItem);
            const option = document.createElement("option");
            option.value = ruleItem.key;
            option.textContent = ruleItem.label;
            this.rule.appendChild(option);
        });

        statusList.forEach(statusItem => {
            this.dataStatusMap.set(statusItem.key+"", statusItem);
            const option = document.createElement("option");
            option.value = statusItem.key;
            option.textContent = statusItem.label;
            this.status.appendChild(option);
        });

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

    // validatePasswordSize = (string, error) => {
    //     if(!(string == null || string === "" || string.length < 8)) {
    //         error.classList.add("is-valid");
    //         error.classList.remove("is-invalid");
    //         return true;
    //     }
    //
    //     error.classList.remove("is-valid");
    //     error.classList.add("is-invalid");
    //     return false;
    //
    // }

    // validatePasswordContainsUpperAndLowerCase = (string, error) => {
    //     if(/[A-Z]/.test(string) && /[a-z]/.test(string)) {
    //         error.classList.add("is-valid");
    //         error.classList.remove("is-invalid");
    //         return true;
    //     }
    //
    //     error.classList.remove("is-valid");
    //     error.classList.add("is-invalid");
    //     return false;
    // }

    // validatePasswordContainsCharNumber = (string, error) => {
    //     if(/[0-9]/.test(string)) {
    //         error.classList.add("is-valid");
    //         error.classList.remove("is-invalid");
    //         return true;
    //     }
    //
    //     error.classList.remove("is-valid");
    //     error.classList.add("is-invalid");
    //     return false;
    // }

    // validatePasswordContainsSpecialCharacters = (string, error) => {
    //     if(/[!@#$%&*?:+-]/.test(string)) {
    //         error.classList.add("is-valid");
    //         error.classList.remove("is-invalid");
    //         return true;
    //     }
    //
    //     error.classList.remove("is-valid");
    //     error.classList.add("is-invalid");
    //     return false;
    // }

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

    // validatePassWord = (password) => {
    //     const value = password.value;
    //     const check = !Array.of(
    //         this.validatePasswordSize(value, this.charLengthError),
    //         this.validatePasswordContainsUpperAndLowerCase(value, this.charUpperLowerError),
    //         this.validatePasswordContainsCharNumber(value, this.charNumberError),
    //         this.validatePasswordContainsSpecialCharacters(value, this.charSpecialError)
    //     ).includes(false);
    //
    //     if(check) {
    //         password.classList.add("is-valid");
    //         password.classList.remove("is-invalid");
    //     }
    //     else {
    //         password.classList.remove("is-valid");
    //         password.classList.add("is-invalid");
    //     }
    //     return check;
    // }

    // validatePasswordEquals(password, passwordValue) {
    //     if(password.value === passwordValue) {
    //         password.classList.add("is-valid");
    //         password.classList.remove("is-invalid");
    //         return true;
    //     }
    //
    //     password.classList.remove("is-valid");
    //     password.classList.add("is-invalid");
    //     return false;
    // }

    validateForm = () => {
        this.submitBtn.disabled =  Array.of(
            this.validateInputRequired(this.firstName),
            this.validateInputRequired(this.lastName),
            this.validateInputRequired(this.pseudo),
            this.validateEmail(this.email),
            this.validateInputRequired(this.channel),
            this.validateDateLimit(this.birthDate),
            this.validateInputRequired(this.rule),
            this.validateInputRequired(this.status)
            // this.validatePassWord(this.password),
            // this.validatePasswordEquals(this.password2, this.password.value)
        ).includes(false);
    }

    dataStreamerEvent = (streamer) => {
        return new CustomEvent("data-streamer-submit", {
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
        this.rule.addEventListener("change", this.validateForm);
        this.status.addEventListener("change", this.validateForm);
        // this.password.addEventListener("keyup", this.validateForm);
        // this.password2.addEventListener("keyup", this.validateForm);

        this.cancelBtn.addEventListener("click", event => {
            this.clear();
        });

        this.submitBtn.addEventListener("click", event => {
            this.extractDataAndSetStreamer();
            this.form.dispatchEvent(this.dataStreamerEvent(this.streamer));
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

    setSelectedIndexByValue(select, value){
        value += "";
        const options = select.options;
        let index = 0;
        for(let i = 0; i < options.length; i++){
            if(options[i].value === value) {
                index = i;
                break;
            }
        }
        select.selectedIndex = index;
    }

    fill = (streamer) => {
        this.streamer = streamer;
        if(this.streamer == null) return;

        this.firstName.value = this.streamer.firstName;
        this.firstName.disabled = true;

        this.lastName.value = this.streamer.lastName;
        this.lastName.disabled = true;

        this.pseudo.value = this.streamer.pseudo;
        this.pseudo.disabled = true;

        this.email.value = this.streamer.email;
        this.email.disabled = true;

        const birthDate= DateUtils.buildDate(this.streamer.birthDate);
        this.birthDate.value = birthDate.getDateFormat_YYYY_MM_DD();
        this.channel.value = this.streamer.channel;
        this.setSelectedIndexByValue(this.rule, this.streamer.rule.key);
        this.setSelectedIndexByValue(this.status, this.streamer.status.key);
    }

    clear = () => {
        this.firstName.disabled = false;
        this.firstName.value = "";
        this.clearValidOrInvalidCSS(this.firstName);

        this.lastName.disabled = false;
        this.lastName.value = "";
        this.clearValidOrInvalidCSS(this.lastName);

        this.pseudo.disabled = false;
        this.pseudo.value = "";
        this.clearValidOrInvalidCSS(this.pseudo);

        this.email.disabled = false;
        this.email.value = "";
        this.clearValidOrInvalidCSS(this.email);

        this.channel.value = "";
        this.clearValidOrInvalidCSS(this.channel);
        this.birthDate.value = "";
        this.clearValidOrInvalidCSS(this.birthDate);
        this.rule.selectedIndex = 0;
        this.clearValidOrInvalidCSS(this.rule);
        this.status.selectedIndex = 0;
        this.clearValidOrInvalidCSS(this.status);
        // this.password.value = "";
        // this.clearValidOrInvalidCSS(this.password);
        // this.password2.value = "";
        // this.clearValidOrInvalidCSS(this.password2);

        // this.clearValidOrInvalidCSS(this.charLengthError);
        // this.clearValidOrInvalidCSS(this.charUpperLowerError);
        // this.clearValidOrInvalidCSS(this.charNumberError);
        // this.clearValidOrInvalidCSS(this.charSpecialError);

        this.submitBtn.disabled = true;

        this.streamer = null;

    }

    extractDataAndSetStreamer = () => {
        if(this.streamer == null) this.streamer = new Streamer();
        this.streamer.firstName = this.firstName.value;
        this.streamer.lastName  = this.lastName.value;
        this.streamer.pseudo    = this.pseudo.value;
        this.streamer.email     = this.email.value;
        this.streamer.birthDate = this.birthDate.value;
        this.streamer.channel   = this.channel.value;
        this.streamer.rule      = Rule.parse(this.dataRuleMap.get(this.rule.value));
        this.streamer.status    = StreamerStatus.parse(this.dataStatusMap.get(this.status.value));
        // this.streamer.password  = this.password.value;
    }
}

export class StreamerModal {
    modal;
    streamerTable;
    form;
    streamer;
    constructor(modalId, streamerTable, form) {
        this.modal = document.getElementById(modalId);
        this.streamerTable = streamerTable;
        this.form = form;
        this.streamer = null;
        this.initEvent();
    }

    initEvent = () => {
        this.modal.addEventListener('show.bs.modal', event => {
            this.form.clear();
            const button = event.relatedTarget
            const recipient = button.getAttribute('data-bs-whatever');
            const modalTitle = this.modal.querySelector('.modal-title');
            modalTitle.textContent = recipient;

            const streamerUUID = button.getAttribute("data-uuid");
            if(streamerUUID != null) {
                this.form.fill(this.streamerTable.getStreamerByUUID(streamerUUID));
            }
        });
    }

    close = () => {
        bootstrap.Modal.getInstance(this.modal).hide();
    }

}