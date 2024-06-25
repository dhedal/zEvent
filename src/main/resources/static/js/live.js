

(function() {
    'use strict';
    document.querySelector('.material-design-hamburger__icon').addEventListener(
        'click',
        function() {
            const addInputListenerFn = (filterId, checkboxId) => {
                const inputFilter = document.getElementById(filterId);
                const checkbox = document.getElementById(checkboxId);

                inputFilter.disabled = true;
                checkbox.addEventListener("click", () => {
                    inputFilter.disabled = !checkbox.checked;
                });
                return inputFilter;
            };

            const dateFilterInput = addInputListenerFn("dateMenuFilterInputId", "dateMenuFilterCheckboxId");
            const thematiqueFilterInput = addInputListenerFn("thematiqueMenuFilterInputId", "thematiqueMenuFilterCheckboxId");
            const streamerFilterInput = addInputListenerFn("streamerMenuFilterInputId", "streamerMenuFilterCheckboxId");

            const liveMenuFilterSubmitBtn = document.getElementById("liveMenuFilterSubmitBtn");
            liveMenuFilterSubmitBtn.addEventListener("click", () => {
                let rq = "";
                if(!dateFilterInput.disabled) {
                    console.log(dateFilterInput.value);
                }

                if(!thematiqueFilterInput.disabled) {
                    console.log(getLiveThematiqueList());
                }

                if(!streamerFilterInput.disabled) {
                    console.log(getStreamerPseudoList());
                }
            });

            document.body.classList.toggle('background--blur');
            this.parentNode.nextElementSibling.classList.toggle('menu--on');

            let child = this.childNodes[1].classList;

            if (child.contains('material-design-hamburger__icon--to-arrow')) {
                child.remove('material-design-hamburger__icon--to-arrow');
                child.add('material-design-hamburger__icon--from-arrow');
            } else {
                child.remove('material-design-hamburger__icon--from-arrow');
                child.add('material-design-hamburger__icon--to-arrow');
            }
        });
})();

