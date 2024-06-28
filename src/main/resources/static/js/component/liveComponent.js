export class LivesCardHandler {
    constructor() {
    }

    static createLiveCard(containerId, lives) {
        const liveCardContainer = document.getElementById(containerId);
        lives.forEach(live => {
            console.log(live);
            const card = document.createElement("live-card");
            liveCardContainer.appendChild(card);
        });
    }

}


export class LiveCard extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({mode: "open"});
    }

    static get observedAttributes() {
        return ["data-item"];
    }

    attributeChangedCallback(name, oldValue, newValue){
        if(name === "data-item") {
            this.render(JSON.parse(newValue));
        }
    }

    set item(value) {
        this.setAttribute("data-item", JSON.stringify(value));
    }

    get item() {
        return JSON.parse(this.getAttribute("data-item"));
    }

    render(live){
        console.log(live);
        let day, time;
        [day, time] = live.dateStart.split("T");

        const wrapper = document.createElement("div");
        wrapper.setAttribute("class", "col");

        const card = document.createElement("div");
        wrapper.appendChild(card);
        card.setAttribute("class", 'card border-primary mb-3');

        // const cardHeader = document.createElement("div");
        // card.appendChild(cardHeader);
        // cardHeader.setAttribute("class", "card-header");
        // cardHeader.textContent = live.streamerPseudo;

        const cardBody = document.createElement("div");
        card.appendChild(cardBody);
        cardBody.setAttribute("class", "card-body");

        const title = document.createElement("h5");
        cardBody.appendChild(title);
        title.setAttribute("class", "card-title fw-bold");
        title.textContent = live.title;

        const subTitle = document.createElement("h6");
        cardBody.appendChild(subTitle);
        // d-flex justify-content-end
        subTitle.setAttribute("class", "card-subtitle mb-2 text-body-secondary");
        subTitle.innerHTML = '<small class="">rendez- vous le <a href="#" class="card-link">'+ day + ' à ' + time +'</a> avec <a href="#" class="card-link m-0">'+live.streamerPseudo+'</a></small>'
        const description = document.createElement("p");
        cardBody.appendChild(description);
        description.setAttribute("class", "card-text text-body-secondary");
        description.textContent = live.description;

        const cardFooter = document.createElement("div");
        card.appendChild(cardFooter);
        cardFooter.setAttribute("class", "card-footer");

        const rdv = document.createElement("p");
        cardFooter.appendChild(rdv);
        rdv.setAttribute("class", "card-text");

        const smallText = document.createElement("small");
        rdv.appendChild(smallText);
        smallText.setAttribute("class", "fs-6 d-flex justify-content-between text-body-secondary");
        smallText.innerHTML = '<a href="#" class="card-link">'+ live.theme.label + '</a>, <a href="#" class="card-link">'+ live.pegi.label + '</a>';



        this.shadowRoot.innerHTML = "";
        this.shadowRoot.appendChild(wrapper);

        const link = document.createElement("link");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("href", "scss/main.css");
        this.shadowRoot.appendChild(link);
    }
}

customElements.define("live-card", LiveCard);