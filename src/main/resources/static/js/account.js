import { ApiService} from "./service/apiService.js";
import {LiveCard, ThemeButtonComponent} from "./component/liveComponent.js";
import {DateUtils} from "./util/dateUtils.js";

let themeMap = new Map();
let pegiMap = new Map();
let themeSelectedMap = new Map();
let streamer;
let lives = null;let sendLiveFormBtn = document.getElementById("sendLiveFormBtn");;
const titleInput = document.getElementById("liveTitle");
const descriptionTextArea = document.getElementById("liveDescription");
const liveThemeSelect = document.getElementById("liveTheme");
const themesSelectedDiv = document.getElementById("themesSelectedDiv");
const livePegiSelect = document.getElementById("livePegi")
const dateStartInput = document.getElementById("dateStart");
const hourStartSelect = document.getElementById("hourStart");
const minuteStartSelect = document.getElementById("minuteStart");
const dateEndInput = document.getElementById("dateEnd");
const hourEndSelect = document.getElementById("hourEnd");
const minuteEndSelect = document.getElementById("minuteEnd");


const createHourOrMinuteOptions = (select, isMinute = false) => {
    const max = isMinute ? 60 : 24;
    let n = 0;
    while(n < max) {
        const option = document.createElement("option");
        option.value = n;
        option.textContent = n < 10 ? "0" + n : n;
        n++;
        select.appendChild(option);
    }
};

const appendOptionElement = (select, list) => {
    list.forEach(element => {
        const option = document.createElement("option");
        option.value = element.key;
        option.textContent = element.label;
        select.appendChild(option);
    });
}

const appendThemeButton = (theme) => {
    const themeBtn = document.createElement("theme-button");
    themeBtn.item = theme;
    themesSelectedDiv.appendChild(themeBtn);

    themeBtn.addEventListener("theme-button-click", event => {
        event.preventDefault();
        event.stopPropagation();
        themeSelectedMap.delete(themeBtn.item.key);
        themesSelectedDiv.removeChild(themeBtn);
        validateForm();
    });
};

const createThemeOptionElement = (select, list) => {
    appendOptionElement(select, list);
    select.addEventListener("change", (event) => {
        if(select.value !== "") {
            if(!themeSelectedMap.has(select.value)){
                themeSelectedMap.set(select.value, themeMap[select.value]);
                appendThemeButton(themeMap[select.value]);
                validateForm();
            }
            select.selectedIndex = 0;
        }
    });

};


const getAndShowLives = () => {
    ApiService.fetchLivesByStreamerPseudo(streamer.pseudo)
        .then(response => {
            lives = response;
            const pane = document.getElementById("lives-tab-pane");
            lives.forEach(live => {
                const liveCard = document.createElement("live-card");
                liveCard.item = live;
                liveCard.isEditable = true;
                pane.appendChild(liveCard);
                liveCard.addEventListener("live-card-edit-click", event => {
                    fillLiveForm(event.detail.item);
                    showTab("live-edit-tab");
                });
            });
        });
};

const validateInputRequired = (input) => {
    if(input.value != ""){
        input.classList.add("is-valid");
        input.classList.remove("is-invalid");
        return true
    }

    input.classList.add("is-invalid");
    input.classList.remove("is-valid");
    return false;
};


const validateLiveDescription = (input) => {
    if(input.value == "") {
        input.classList.remove("is-valid");
        input.classList.remove("is-invalid");
        return true;
    }
    else if(input.value.length > 0 && input.value.length <= 250){
        input.classList.add("is-valid");
        input.classList.remove("is-invalid");
        return true
    }

    input.classList.add("is-invalid");
    input.classList.remove("is-valid");
    return false;
};

const validateThemesSelected = (themeSelectElt, themeSelectedDivElt) => {
    if(themeSelectedDivElt.children.length > 0) {
        themeSelectElt.classList.add("is-valid");
        themeSelectElt.classList.remove("is-invalid");
        return true
    }
    themeSelectElt.classList.add("is-invalid");
    themeSelectElt.classList.remove("is-valid");
    return false;
};

const validateForm = () => {
    const check = Array.of(
        validateInputRequired(titleInput),
        validateLiveDescription(descriptionTextArea),
        validateThemesSelected(liveThemeSelect, themesSelectedDiv),
        validateInputRequired(livePegiSelect),
        validateInputRequired(dateStartInput),
        validateInputRequired(hourStartSelect),
        validateInputRequired(minuteStartSelect),
        validateInputRequired(dateEndInput),
        validateInputRequired(hourEndSelect),
        validateInputRequired(minuteEndSelect)
    );
    sendLiveFormBtn.disabled = check.includes(false);
}


titleInput.addEventListener("keyup", validateForm);
descriptionTextArea.addEventListener("keyup", validateForm);
livePegiSelect.addEventListener("change", validateForm);
dateStartInput.addEventListener("change", validateForm);
hourStartSelect.addEventListener("change", validateForm);
minuteStartSelect.addEventListener("change", validateForm);
dateEndInput.addEventListener("change", validateForm);
hourEndSelect.addEventListener("change", validateForm);
minuteEndSelect.addEventListener("change", validateForm);

const selectOptionByValue = (select, value) => {
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

const clearValidOrInvalidCSS = (element) => {
    if(element.classList.contains("is-valid")) {
        element.classList.remove("is-valid");
    }
    else {
        element.classList.remove("is-invalid");
    }
};
const clearLiveForm = () => {
    titleInput.value = "";
    clearValidOrInvalidCSS(titleInput);

    descriptionTextArea.value = "";
    clearValidOrInvalidCSS(descriptionTextArea);

    themesSelectedDiv.innerHTML = "";
    themeSelectedMap.clear();
    clearValidOrInvalidCSS(liveThemeSelect);

    livePegiSelect.selectedIndex = 0;
    clearValidOrInvalidCSS(livePegiSelect);

    dateStartInput.value = "";
    clearValidOrInvalidCSS(dateStartInput);
    hourStartSelect.selectedIndex = 0;
    clearValidOrInvalidCSS(hourStartSelect);
    minuteStartSelect.selectedIndex = 0;
    clearValidOrInvalidCSS(minuteStartSelect);

    dateEndInput.value = "";
    clearValidOrInvalidCSS(dateEndInput);
    hourEndSelect.selectedIndex = 0;
    clearValidOrInvalidCSS(hourEndSelect);
    minuteEndSelect.selectedIndex = 0;
    clearValidOrInvalidCSS(minuteEndSelect);

    sendLiveFormBtn.disabled = true;
};
const fillThemesSelectedDiv = (themes) => {
    themes.forEach(theme => {
        themeSelectedMap.set(theme.key, theme);
        appendThemeButton(theme);
    });
};
const fillLiveForm = (live) => {
    titleInput.value = live.title;
    descriptionTextArea.value = live.description;

    fillThemesSelectedDiv(live.themes);


    selectOptionByValue(livePegiSelect, live.pegi.key + "");

    const dateStart = DateUtils.buildDate(live.dateStart);
    dateStartInput.value = dateStart.getDateFormat_YYYY_MM_DD();
    selectOptionByValue(hourStartSelect, dateStart.getHoursToString());
    selectOptionByValue(minuteStartSelect, dateStart.getMinutesToString());

    const dateEnd = DateUtils.buildDate(live.dateEnd);
    dateEndInput.value = dateStart.getDateFormat_YYYY_MM_DD();
    selectOptionByValue(hourEndSelect, dateEnd.getHoursToString());
    selectOptionByValue(minuteEndSelect, dateEnd.getMinutesToString());
    validateForm();
};

const showTab = (tabId) => {
    new bootstrap.Tab(document.getElementById(tabId)).show();
}

(function() {

    ApiService.fetchStreamerByPseudo("HankTaylor").then(response => {
        streamer = response;
        getAndShowLives();

    });

    ApiService.fetchThemeAndPegiList().then(response => {
        if(response.themes !== null) {
            response.themes.forEach(theme => themeMap[theme.key] = theme);
        }

        if(response.pegis !== null) {
            response.pegis.forEach(pegi => pegiMap[pegi.key] = pegi);
        }
        createThemeOptionElement(liveThemeSelect, response.themes);
        appendOptionElement(livePegiSelect, response.pegis);
        createHourOrMinuteOptions(hourStartSelect);
        createHourOrMinuteOptions(minuteStartSelect, true);
        createHourOrMinuteOptions(hourEndSelect);
        createHourOrMinuteOptions(minuteEndSelect, true);



    });

    const tabEl = document.querySelector('button[data-bs-toggle="tab"]');
    tabEl.addEventListener("shown.bs.tab", event => {
        if("live-edit-tab" === event.originalTarget.id) {
            if(lives == null) {
                getAndShowLives();
            }
        }
    });

    const livesTabBtn = document.getElementById("lives-tab-btn");
    livesTabBtn.addEventListener("click", event => {
        showTab("lives-tab");
    });


    sendLiveFormBtn.disabled  = true;
    sendLiveFormBtn.addEventListener("click", event => {
        console.log(sendLiveFormBtn);
    });

    const cancelLiveFormBtn = document.getElementById("cancelLiveFormBtn");
    cancelLiveFormBtn.addEventListener("click", event => {
        clearLiveForm();
    });
})();