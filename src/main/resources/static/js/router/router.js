import {Route, ADMIN_AUTORIZE, STREAMER_AUTORIZE} from "./route.js"
import {allRoutes, websiteName} from "./allRoutes.js";
import {AuthService} from "../service/authService.js";

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
export const loadContentPage = async () => {
    const path = window.location.pathname;

    const actualRoute = getRouteByUrl(path);

    // TODO: vérifier si l'utilsateur à le droit d'accés à cette page

    let config = {};
    if(actualRoute.autorize.length > 0) {
        console.log(actualRoute.autorize);
        const rule = AuthService.getRule();
        if(actualRoute.autorize.includes(rule) || actualRoute.autorize.includes(rule)){
            config = {
                method: "GET",
                headers: {"Authorization" : `Bearer ${AuthService.getToken()}`}
            };
        }
        else {
            return;
        }

    }



    const html = await fetch(actualRoute.pathHtml, config).then((data) => data.text());
    document.getElementById("main-page").innerHTML = html;

    if(actualRoute.pathJs != ""){
        let scripTag = document.createElement("script");
        scripTag.setAttribute("type", "module");
        scripTag.setAttribute("src", actualRoute.pathJs);
        document.querySelector("body").appendChild(scripTag);
    }

    document.title = actualRoute.title + " - " + websiteName;

    // TODO: afficher/masquer les élements en fonction du rôle
    showAndHideElementsForRules();
};

export const gotTo = (path) => {
    window.location.href = path ? path : "/";
};

const makeVisible = (elt, isVisible) => {
    if(isVisible) {
        elt.classList.add("visible");
        elt.classList.remove("invisible");
    }
    else {
        elt.classList.add("invisible");
        elt.classList.remove("visible");
    }
};

const showAndHideElementsForRules = () => {
    const allElementToEdit = document.querySelectorAll('[data-show]');
    allElementToEdit.forEach(elt => {
        let visible = false;
        switch(elt.dataset.show) {
            case "streamer":
                visible = AuthService.isStreamer() || AuthService.isAdmin();
                break;
            case "admin":
                visible = AuthService.isAdmin();
                break;
            case "disconnect" :
                visible = !AuthService.isConnected();
                break;
            case "connected" :
                visible = AuthService.isConnected();
                break;
        }
        makeVisible(elt, visible);
    });
}

document.getElementById("disconnectBtn").addEventListener("click", event => {
    AuthService.disconnect();
    gotTo("/");
});



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

