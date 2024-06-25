import Route from "./route.js";

export const allRoutes = [
    new Route("/", "Accueil", "/pages/home.html", [], ""),
    new Route("/live", "Live", "/pages/live.html", [], "/js/live.js")
];

export const websiteName = "Z-Event";