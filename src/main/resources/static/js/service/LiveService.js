import {API_URL} from "./apiService.js";
import {AuthService} from "./authService.js";

const API_LIVE_URL = API_URL + "live"
export class LiveService {

    static fetchThemesAndPegiList = async () => {
        const response = await fetch( `${API_LIVE_URL}/theme-and-pegi-list`);
        return await response.json();
    };

    static fetchLivesByStreamerUuid = async (streamerUuid) => {
        const response = await fetch(`${API_LIVE_URL}/streamer/${streamerUuid}`);
        return await response.json();
    }

    static fetchMyLives = async () => {
        let streamer = null;
        if(AuthService.isAdmin() || AuthService.isStreamer()) {
            streamer = AuthService.getStreamer();
        }
        return streamer ? LiveService.fetchLivesByStreamerUuid(streamer.uuid) : [];
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