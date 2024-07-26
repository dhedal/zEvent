import {Route, STREAMER_AUTORIZE, ADMIN_AUTORIZE} from "./route.js";

export const allRoutes = [
    new Route("/", "Accueil", "/pages/home.html", [], ""),
    new Route("/live", "Live", "/pages/live.html", [], "/js/pages/live.js"),
    new Route("/account", "Mon Compte", "/pages/private/account.html", [STREAMER_AUTORIZE, ADMIN_AUTORIZE], "/js/pages/private/account.js"),
    new Route("/admin", "Admin", "/pages/private/admin.html", [ADMIN_AUTORIZE], "/js/pages/private/admin.js"),
    new Route("/signin", "Inscription", "/pages/signin.html", [], "/js/pages/signin.js"),
    new Route("/signup", "Connexion", "/pages/signup.html", [], "/js/pages/signup.js")
];

export const websiteName = "Z-Event";