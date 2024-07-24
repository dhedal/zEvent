
export class PageLoaderService {
    constructor() {
    }

    loadPage = async (route) => {

    }

    sanitizeHtml = (text) => {
        const tempHtml = document.createElement("div");
        tempHtml.textContent = text;
        return tempHtml.innerHTML;
    }


}