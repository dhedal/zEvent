
export const STREAMER_AUTORIZE = "streamer";
export const ADMIN_AUTORIZE = "admin";
export class Route {
    constructor(url, title, pathHtml, autorize = new Array(), pathJs = "") {
        this.url = url;
        this.title = title;
        this.pathHtml = pathHtml;
        this.autorize = autorize;
        this.pathJs = pathJs
    }
}