const API_URL = "http://localhost:8080/api/";
const API_STREAMER_URL = API_URL + "streamer/";
const API_LIVE_URL = API_URL + "live/";

export class ApiService {
    static fetchStreamerPseudoList = async () => {
        const response = await fetch(API_STREAMER_URL + "pseudo/list");
        return await response.json();
    };

    static fetchLiveThematiqueList = async () => {
        const response = await fetch(API_LIVE_URL + "thematique/list");
        return await response.json();
    }

    static fetchLives = async (rq) => {
        const response = await fetch(API_LIVE_URL + "list/param" + rq);
        return await response.json();
    };

    static fetchTodayAndUpcomingLives = async () => {
        const response = await fetch(API_LIVE_URL + "list/dateStart/greaterThanEquals");
        return await response.json();
    }

    static fetchStreamerByPseudo = async (pseudo) => {
        const response = await fetch(API_STREAMER_URL + "pseudo/" + pseudo);
        return await response.json();
    }
}








