import Route from "./route.js";

export const allRoutes = [
    new Route("/", "Accueil", "/pages/home.html", [], ""),
    new Route("/live", "Live", "/pages/live.html", [], "/js/live.js"),
    new Route("/account", "Mon Compte", "/pages/account.html", [], "/js/account.js")
];

export const websiteName = "Z-Event";