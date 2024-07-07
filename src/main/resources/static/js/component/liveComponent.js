export const extractThemeLabel = (themes) => {
    return themes.map((t) => t.label).join(", ");
}

export class LiveCard extends HTMLElement {
    constructor(isEditable = false) {
        super();
        this.attachShadow({mode: "open"});
    }

    onDetailsClick() {
        const event = new CustomEvent("live-card-details-click", {
            detail: {item : this.item},
            bubbles: true,
            composed: true
        });
        this.dispatchEvent(event);
    }

    onEditClick() {
        const event = new CustomEvent("live-card-edit-click", {
            detail: {item : this.item},
            bubbles: true,
            composed: true
        });
        this.dispatchEvent(event);
    }


    static get observedAttributes() {
        return ["data-item", "is-editable"];
    }

    attributeChangedCallback(name, oldValue, newValue){
        if(name === "data-item" && oldValue !== newValue) {
            this.item = JSON.parse(newValue);
        }
        if(name === "is-editable" && oldValue !== newValue) {
            this.isEditable = newValue === 'true';
        }
        this.render();
    }

    set item(value) {
        const newValue = JSON.stringify(value);
        if(newValue !== this.getAttribute("data-item")) {
            this.setAttribute("data-item", newValue);
        }
    }

    set isEditable(value) {
        const newValue = JSON.stringify(value);
        if(newValue !== this.getAttribute("is-editable")) {
            this.setAttribute("is-editable", newValue);
        }
    }

    get item() {
        return JSON.parse(this.getAttribute("data-item"));
    }

    get isEditable() {
        return this.getAttribute("is-editable" ) === 'true';
    }

    render(){
        const live = this.item;
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
        subTitle.innerHTML = '<small class="">rendez- vous le <span class="card-link">'+ day + ' à ' + time +'</span> avec <span class="card-link m-0">'+live.streamerPseudo+'</span></small>'
        const description = document.createElement("p");
        cardBody.appendChild(description);
        description.setAttribute("class", "card-text text-body-secondary");
        description.textContent = live.description;

        const liveLink = document.createElement("small");
        cardBody.appendChild(liveLink);
        liveLink.setAttribute("class", "d-flex justify-content-end");
        liveLink.innerHTML = '<a href="javascript:void(0);" class="card-link">' + (this.isEditable ? 'édit' : 'détails') +'</a>';
        liveLink.addEventListener("click", () => this.isEditable ? this.onEditClick() : this.onDetailsClick());


        const cardFooter = document.createElement("div");
        card.appendChild(cardFooter);
        cardFooter.setAttribute("class", "card-footer");

        const rdv = document.createElement("p");
        cardFooter.appendChild(rdv);
        rdv.setAttribute("class", "card-text");

        const smallText = document.createElement("small");
        rdv.appendChild(smallText);
        smallText.setAttribute("class", "fs-6 d-flex justify-content-between text-body-secondary");
        smallText.innerHTML = '<span class="card-link">'+ extractThemeLabel(live.themes) + '</span>, <span class="card-link">'+ live.pegi.label + '</span>';

        this.shadowRoot.innerHTML = "";
        this.shadowRoot.appendChild(wrapper);

        const link = document.createElement("link");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("href", "scss/main.css");
        this.shadowRoot.appendChild(link);


    }
}
customElements.define("live-card", LiveCard);

export class ThemeButtonComponent extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({mode: "open"});
    }

    onThemeButtonClick() {
        const event = new CustomEvent("theme-button-click", {
            detail: {item : this.item},
            bubbles: true,
            composed: true
        });
        this.dispatchEvent(event);
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

    getId() {
        return "themeButton-" + this.item.key;
    }

    render(theme){

        const btnGroup = document.createElement("div");
        btnGroup.setAttribute("id", this.getId());
        btnGroup.setAttribute("class","btn-group m-2");
        btnGroup.addEventListener("click", event => this.onThemeButtonClick());


        const btnPart1 = document.createElement("button");
        btnGroup.appendChild(btnPart1);
        btnPart1.setAttribute("class", "btn btn-outline-primary");
        btnPart1.textContent = theme.label;

        const btnPart2 = document.createElement("button");
        btnGroup.appendChild(btnPart2);
        btnPart2.setAttribute("class", "btn btn-primary");
        btnPart2.textContent = "X";

        this.shadowRoot.innerHTML = "";
        this.shadowRoot.appendChild(btnGroup);

        const link = document.createElement("link");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("href", "scss/main.css");
        this.shadowRoot.appendChild(link);
    }
}

customElements.define("theme-button", ThemeButtonComponent);