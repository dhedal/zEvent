import { ApiService} from "./service/apiService.js";
import {LiveCard, ThemeButtonComponent} from "./component/liveComponent.js";
import {DateUtils} from "./util/dateUtils.js";
import {Live, Pegi, Streamer, Theme} from "./model/models.js";

const dataThemeMap = new Map();
const dataPegiMap = new Map();
const dataLiveMap = new Map();
let form;

const fetchDatas = async () => {
    return Promise.all([
        ApiService.fetchThemeAndPegiList().then(response => {
            if(response.themes !== null) {
                response.themes.forEach(theme => dataThemeMap.set(theme.key+"", theme));
            }

            if(response.pegis !== null) {
                response.pegis.forEach(pegi => dataPegiMap.set(pegi.key+"", pegi));
            }

        }),
        ApiService.fetchStreamerByPseudo("HankTaylor").then(response => {
            const streamer = Streamer.parse(response);

            return ApiService.fetchLivesByStreamerPseudo(streamer.pseudo)
                .then(response => {
                    response.forEach(live => {
                        dataLiveMap.set(live.uuid, live);
                    });
                });
        })
    ]);

};

const showLivesTab = (tabId) => {
    new bootstrap.Tab(document.getElementById(tabId)).show();
}

const addDataLivesInLiveTabPane = () => {
    const pane = document.getElementById("lives-tab-pane");
    dataLiveMap.forEach((value, key, map) => {
        const liveCard = document.createElement("live-card");
        liveCard.item = value;
        liveCard.isEditable = true;
        pane.appendChild(liveCard);
        liveCard.addEventListener("live-card-edit-click", event => {
            form.fill(event.detail.item);
            showLivesTab("live-edit-tab");
        });
    });
}

class LiveForm {
    title;
    description;
    themes;
    themesContainer;
    themesMap;
    pegi;
    dateStart;
    hourStart;
    minuteStart;
    dateEnd;
    hourEnd;
    minuteEnd;
    sendBtn;
    cancelBtn;

    constructor() {
        this.title = document.getElementById("liveTitle");
        this.description = document.getElementById("liveDescription");
        this.themes = document.getElementById("liveTheme");
        this.themesContainer = document.getElementById("themesSelectedDiv");
        this.themesMap = new Map();
        this.pegi = document.getElementById("livePegi")
        this.dateStart = document.getElementById("dateStart");
        this.hourStart = document.getElementById("hourStart");
        this.minuteStart = document.getElementById("minuteStart");
        this.dateEnd = document.getElementById("dateEnd");
        this.hourEnd = document.getElementById("hourEnd");
        this.minuteEnd = document.getElementById("minuteEnd");
        this.sendBtn = document.getElementById("sendLiveFormBtn");
        this.cancelBtn = document.getElementById("cancelLiveFormBtn");
    }

    setSelectedIndexByValue(select, value){
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

    buildForm(themeList, pegiList) {
        this.buildThemesOptions(themeList);
        this.buildPegiOptions(pegiList);
        this.buildHourOrMinuteOptions(this.hourStart);
        this.buildHourOrMinuteOptions(this.minuteStart, true);
        this.buildHourOrMinuteOptions(this.hourEnd);
        this.buildHourOrMinuteOptions(this.minuteEnd, true);

        this.sendBtn.disabled = true;
        this.buildFormEvent();
    }

    buildHourOrMinuteOptions(select, isMinute = false) {
        const max = isMinute ? 60 : 24;
        let n = 0;
        while(n < max) {
            const option = document.createElement("option");
            option.value = n;
            option.textContent = n < 10 ? "0" + n : n;
            n++;
            select.appendChild(option);
        }
    }

    buildThemesOptions(themesList) {
        themesList.forEach(theme => {
            const option = document.createElement("option");
            option.value = theme.key;
            option.textContent = theme.label;
            this.themes.appendChild(option);
        });
    }

    buildPegiOptions(pegiList) {
        pegiList.forEach(pegi => {
            const option = document.createElement("option");
            option.value = pegi.key;
            option.textContent = pegi.label;
            this.pegi.appendChild(option);
        });
    }

    buildFormEvent() {
        this.themes.addEventListener("change", (event) => {
            if(this.themes.value !== "") {
                this.addThemes(Array.of(dataThemeMap.get(this.themes.value)));
                this.themes.selectedIndex = 0;
            }
        });

        this.title.addEventListener("keyup", this.validateForm);
        this.description.addEventListener("keyup", this.validateForm);
        this.pegi.addEventListener("change", this.validateForm);
        this.dateStart.addEventListener("change", this.validateForm);
        this.hourStart.addEventListener("change", this.validateForm);
        this.minuteStart.addEventListener("change", this.validateForm);
        this.dateEnd.addEventListener("change", this.validateForm);
        this.hourEnd.addEventListener("change", this.validateForm);
        this.minuteEnd.addEventListener("change", this.validateForm);

        this.sendBtn.addEventListener("click", event => {
            const live = this.extractData();
            console.log(live);
        });

        this.cancelBtn.addEventListener("click", event => {
            this.clear();
        });

    }

    addThemes(themeArray) {
        themeArray.forEach(theme => {
            if(this.themesMap.has(theme.key)) return;
            this.themesMap.set(theme.key, theme);
            const themeBtn = document.createElement("theme-button");
            themeBtn.item = theme;
            this.themesContainer.appendChild(themeBtn);

            themeBtn.addEventListener("theme-button-click", event => {
                event.preventDefault();
                event.stopPropagation();
                this.themesMap.delete(themeBtn.item.key);
                this.themesContainer.removeChild(themeBtn);
                this.validateForm();
            });
        });

        this.validateForm();

    }

    fill = (live) => {
        this.title.value = live.title;
        this.description.value = live.description;

        this.addThemes(live.themes);

        this.setSelectedIndexByValue(this.pegi, live.pegi.key+"");

        let dateUtils = DateUtils.buildDate(live.dateStart);
        this.dateStart.value = dateUtils.getDateFormat_YYYY_MM_DD();
        this.setSelectedIndexByValue(this.hourStart, dateUtils.getHoursToString());
        this.setSelectedIndexByValue(this.minuteStart, dateUtils.getMinutesToString());

        dateUtils = DateUtils.buildDate(live.dateEnd);
        this.dateEnd.value = dateUtils.getDateFormat_YYYY_MM_DD();
        this.setSelectedIndexByValue(this.hourEnd, dateUtils.getHoursToString());
        this.setSelectedIndexByValue(this.minuteEnd, dateUtils.getMinutesToString());
        this.validateForm();
    };

    validateForm = () => {
        const check = Array.of(
            this.validateInputRequired(this.title),
            this.validateDescription(this.description),
            this.validateThemes(this.themes, this.themesContainer),
            this.validateInputRequired(this.pegi),
            this.validateInputRequired(this.dateStart),
            this.validateInputRequired(this.hourStart),
            this.validateInputRequired(this.minuteStart),
            this.validateInputRequired(this.dateEnd),
            this.validateInputRequired(this.hourEnd),
            this.validateInputRequired(this.minuteEnd)
        );
        this.sendBtn.disabled = check.includes(false);
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

    validateDescription = (input) => {
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

    validateThemes = (themeSelectElt, themeSelectedDivElt) => {
        if(themeSelectedDivElt.children.length > 0) {
            themeSelectElt.classList.add("is-valid");
            themeSelectElt.classList.remove("is-invalid");
            return true
        }
        themeSelectElt.classList.add("is-invalid");
        themeSelectElt.classList.remove("is-valid");
        return false;
    };

    clearValidOrInvalidCSS = (element) => {
        if(element.classList.contains("is-valid")) {
            element.classList.remove("is-valid");
        }
        else {
            element.classList.remove("is-invalid");
        }
    }

    clear = () => {
        this.title.value = "";
        this.clearValidOrInvalidCSS(this.title);

        this.description.value = "";
        this.clearValidOrInvalidCSS(this.description);

        this.themesContainer.innerHTML = "";
        this.themesMap.clear();
        this.clearValidOrInvalidCSS(this.themes);

        this.pegi.selectedIndex = 0;
        this.clearValidOrInvalidCSS(this.pegi);

        this.dateStart.value = "";
        this.clearValidOrInvalidCSS(this.dateStart);
        this.hourStart.selectedIndex = 0;
        this.clearValidOrInvalidCSS(this.hourStart);
        this.minuteStart.selectedIndex = 0;
        this.clearValidOrInvalidCSS(this.minuteStart);

        this.dateEnd.value = "";
        this.clearValidOrInvalidCSS(this.dateEnd);
        this.hourEnd.selectedIndex = 0;
        this.clearValidOrInvalidCSS(this.hourEnd);
        this.minuteEnd.selectedIndex = 0;
        this.clearValidOrInvalidCSS(this.minuteEnd);

        this.sendBtn.disabled = true;
    }

    extractData() {
        const live = new Live();
        live.title = this.title.value;
        live.description = this.description;
        live.pegi = dataPegiMap.get(this.pegi.value);
        live.themes = [...this.themesMap.values()];
        live.dateStart = DateUtils.parseToDate(this.dateStart.value, this.hourStart.value, this.minuteStart.value);
        live.dateEnd = DateUtils.parseToDate(this.dateEnd.value, this.hourEnd.value, this.minuteEnd.value);
        return live;
    }
}

(function() {

    fetchDatas().then(() => {
        addDataLivesInLiveTabPane();
        form = new LiveForm();
        form.buildForm(Array.from(dataThemeMap.values()), Array.from(dataPegiMap.values()));

        // const tabEl = document.querySelector('button[data-bs-toggle="tab"]');
        // tabEl.addEventListener("shown.bs.tab", event => {
        //     if("live-edit-tab" === event.originalTarget.id) {}
        // });

        const livesTabBtn = document.getElementById("lives-tab-btn");
        livesTabBtn.addEventListener("click", event => {
            showLivesTab("lives-tab");
        });
    });

})();

// TODO: il faut créer le service d'authentification avant d'envoyer et enregistrer les données du live;
// TODO: un live peut être modifié
// TODO: l'onglet nombre d'utilsateur inscrit par live
// TODO: l'onglet statistique