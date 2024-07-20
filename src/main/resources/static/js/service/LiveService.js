import {API_URL} from "./apiService.js";

const API_LIVE_URL = API_URL + "live"
export class LiveService {

    static fetchLiveThematiqueList = async () => {
        const response = await fetch(API_LIVE_URL + "thematique/list");
        return await response.json();
    }

    static fetchLives = async (rq) => {
        const response = await fetch(API_LIVE_URL + "list/param" + rq);
        return await response.json();
    };

    static fetchLivesByStreamerPseudo = async (pseudo) => {
        const rq = "/NONE/NONE/" + pseudo;
        const response = await fetch(API_LIVE_URL + "list/param" + rq);
        return await response.json();
    }

    static fetchTodayAndUpcomingLives = async () => {
        const response = await fetch(API_LIVE_URL + "list/dateStart/greaterThanEquals");
        return await response.json();
    }
}