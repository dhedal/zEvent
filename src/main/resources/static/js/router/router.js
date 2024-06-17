import Route from "./route.js"
import {allRoutes, websiteName} from "./allRoutes.js";

const route404 = new Route("404", "Page introuvable", "/pages/404.html", []);

//
const getRouteByUrl = (url) => {
    let currentRoute = null;
    allRoutes.forEach((element) => {
        if(element.url == url) currentRoute = element;
    });
    if( currentRoute != null) return currentRoute;
    return route404;
};

//
const loadContentPage = async () => {
    const path = window.location.pathname;

    const actualRoute = getRouteByUrl(path);


    // TODO: vérifier si l'utilsateur à le droit d'accés à cette page

    const html = await fetch(actualRoute.pathHtml).then((data) => data.text());
    document.getElementById("main-page").innerHTML = html;

    if(actualRoute.pathJs != ""){
        let scripTag = document.createElement("script");
        scripTag.setAttribute("type", "text/javascript");
        scripTag.setAttribute("src", actualRoute.pathJs);
        document.querySelector("body").appendChild(scripTag);
    }

    document.title = actualRoute.title + " - " + websiteName;

    // TODO: afficher/masquer les élements en fonction du rôle
};

//
const routeEvent = (event) => {
    event.preventDefault();
    window.history.pushState({}, "", event.target.href);
    loadContentPage();
};

// Gestion de l'événement de reoutr en arrière dans l'historique du navigateur
window.onpopstate = loadContentPage;
// Assignation de la fonction routeEvent à la propriété route de la fenêtre
window.route = routeEvent;

loadContentPage();