import { ApiService} from "./service/apiService.js";
import {LiveCard} from "./component/liveComponent.js";

(function() {
    const toggleFilterMenu = () => {
        document.body.classList.toggle('background--blur');
        openCloseFilterBtn.parentNode.nextElementSibling.classList.toggle('menu--on');
        const child = openCloseFilterBtn.childNodes[1].classList;

        if (child.contains('material-design-hamburger__icon--to-arrow')) {
            child.remove('material-design-hamburger__icon--to-arrow');
            child.add('material-design-hamburger__icon--from-arrow');
        } else {
            child.remove('material-design-hamburger__icon--from-arrow');
            child.add('material-design-hamburger__icon--to-arrow');
        }
    };

    const openCloseFilterBtn = document.querySelector(".material-design-hamburger__icon");
    openCloseFilterBtn.addEventListener("click", () => {
        toggleFilterMenu();
    });
    const fillLives = (lives) => {
        const livesCardContainer = document.getElementById("livesCardContainer");
        if(livesCardContainer.classList.contains("visually-hidden")){
            livesCardContainer.classList.toggle("visually-hidden");
        }
        livesCardContainer.innerHTML = "";
        lives.forEach(live => {
            const liveCardDetailsContainer = document.getElementById("liveCardDetailContainer");
            if(!liveCardDetailsContainer.classList.contains("visually-hidden")) {
                liveCardDetailsContainer.classList.toggle("visually-hidden");
            }
            const liveCard = document.createElement("live-card");
            liveCard.item = live;
            livesCardContainer.appendChild(liveCard);
            liveCard.addEventListener("live-card-details-click", (event) => {
                if(!livesCardContainer.classList.contains("visually-hidden")){
                    livesCardContainer.classList.toggle("visually-hidden");
                }
                showLiveCardDetails(event.detail.item);
            });
        });
    };

    const showLiveCardDetails = (live) => {
        const liveCardDetailsContainer = document.getElementById("liveCardDetailContainer");
        if(liveCardDetailsContainer.classList.contains("visually-hidden")) {
            liveCardDetailsContainer.classList.toggle("visually-hidden");
        }

        ApiService.fetchStreamerByPseudo(live.streamerPseudo).then(streamer => {
            console.log(streamer);
            document.getElementById("streamerPseudoId").textContent = streamer.pseudo;
            document.getElementById("chaineId").textContent = streamer.chaine;
            document.getElementById("liveTitleId").textContent = live.title;
            document.getElementById("liveDescriptionId").textContent = live.description;
            document.getElementById("thematiqueId").textContent = live.theme.label;
            document.getElementById("pegiId").textContent = live.pegi.label;
            document.getElementById("showStreamLivesBtn").addEventListener("click", event => {
                const rq = "/NONE/NONE/" + streamer.pseudo;
                ApiService.fetchLives(rq).then(lives => fillLives(lives));
            });
        });

    };

    const loadLiveScript = () => {
        'use strict';
        const submitBtn = document.getElementById("liveMenuFilterSubmitBtn");

        const dateMenuFilterCheckbox = document.getElementById("dateMenuFilterCheckboxId");
        const dateMenuFilterInput = document.getElementById("dateMenuFilterInputId");
        dateMenuFilterCheckbox.addEventListener("click", () => {
            dateMenuFilterInput.classList.toggle("visually-hidden");
            if(dateMenuFilterCheckbox.checked) dateMenuFilterInput.value = "";
        });

        const thematiqueSelect = document.getElementById("thematiqueSelectId");
        ApiService.fetchLiveThematiqueList().then(thematiqueList => {
            thematiqueList.forEach(thematique => {
                const option = document.createElement("option");
                option.value = thematique.label;
                option.textContent = thematique.label;
                thematiqueSelect.appendChild(option);
            });
        });
        const thematiqueMenuFilterCheckbox = document.getElementById("thematiqueCheckboxId");
        thematiqueMenuFilterCheckbox.addEventListener("click", () => {
            thematiqueSelect.classList.toggle("visually-hidden");
        });

        const streamerPseudoSelect = document.getElementById("streamerSelectId");
        ApiService.fetchStreamerPseudoList().then(pseudoList => {
            pseudoList.forEach(pseudo => {
                const option = document.createElement("option");
                option.value = pseudo;
                option.textContent = pseudo;
                streamerPseudoSelect.appendChild(option);
            });
        });
        const streamerPseudoCheckbox = document.getElementById("streamerPseudoCheckboxId");
        streamerPseudoCheckbox.addEventListener("click", () => {
            streamerPseudoSelect.classList.toggle("visually-hidden");
        });

        submitBtn.addEventListener("click", () => {
            let rq = "/" + (dateMenuFilterCheckbox.checked ? dateMenuFilterInput.value : "NONE");
            rq += "/" + (thematiqueMenuFilterCheckbox.checked ? thematiqueSelect.value : "NONE");
            rq += "/" + (streamerPseudoCheckbox.checked ? streamerPseudoSelect.value : "NONE");
            if("/NONE/NONE/NONE" === rq) {
                ApiService.fetchTodayAndUpcomingLives().then(lives => fillLives(lives));
            }
            else {
                ApiService.fetchLives(rq).then(lives => fillLives(lives));
            }

            toggleFilterMenu();

        });
    };

    loadLiveScript();
    ApiService.fetchTodayAndUpcomingLives().then(lives => fillLives(lives));
})();

// document.addEventListener('DOMContentLoaded', () => {});
// TODO: faire le menuFilter (css)
// TODO: refaire le switch en l'affichage de la liste de live et du live details
// TODO: revoir la partie responsive
