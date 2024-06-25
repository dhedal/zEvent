const API_URL = "http://localhost:8080/api/";

/**
 * fonction qui permet d'éviter
 * les failles XSS injection HTML
 * @param text
 * @returns {string}
 */
const sanitizeHtml = (text) => {
    const tempHtml = document.createElement("div");
    tempHtml.textContent = text;
    return tempHtml.innerHTML;
};

const getStreamerPseudoList = async () => {
    const response = await fetch(API_URL + "streamer/pseudo/list");
    const names = await response.json();
    return names;
}

const getLiveThematiqueList = async () => {
    const response = await fetch(API_URL + "live/thematique/list");
    const thematiques = await response.json();
    return thematiques;
}