import Route from "./route.js";

export const allRoutes = [
    new Route("/", "Accueil", "/pages/home.html", [], ""),
    new Route("/live", "Live", "/pages/live.html", [], "/js/live.js"),
    new Route("/account", "Mon Compte", "/pages/account.html", [], "/js/account.js"),
    new Route("/admin", "Admin", "/pages/admin.html", [], "/js/pages/admin.js"),
    new Route("/signin", "Inscription", "/pages/auth/signin.html", [], "/js/auth/auth.js"),
    new Route("/signup", "Connexion", "/pages/auth/signup.html", [], "/js/auth/signup.js")
];

export const websiteName = "Z-Event";