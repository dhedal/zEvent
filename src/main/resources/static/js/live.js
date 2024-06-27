import {fetchLiveThematiqueList, fetchStreamerPseudoList, fetchLives} from "./service/apiService.js";

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
    fetchLiveThematiqueList().then(thematiqueList => {
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
    fetchStreamerPseudoList().then(pseudoList => {
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

    const openCloseFilterBtn = document.querySelector(".material-design-hamburger__icon");
    openCloseFilterBtn.addEventListener("click", () => {
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
        });


    submitBtn.addEventListener("click", () => {
        let rq = "/" + (dateMenuFilterCheckbox.checked ? dateMenuFilterInput.value : "NONE");
        rq += "/" + (thematiqueMenuFilterCheckbox.checked ? thematiqueSelect.value : "NONE");
        rq += "/" + (streamerPseudoCheckbox.checked ? streamerPseudoSelect.value : "NONE");
        console.log(rq);
        fetchLives(rq).then(response => {
            console.log(response);
        });
    });
};

(function() {
    loadLiveScript();
})();
