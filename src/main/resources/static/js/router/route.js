export default class Route {
    constructor(url, title, pathHtml, autorize, pathJs = "") {
        this.url = url;
        this.title = title;
        this.pathHtml = pathHtml;
        this.autorize = autorize;
        this.pathJs = pathJs
    }
}