
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
