const API_URL = "http://localhost:8080/api/";
const API_STREAMER_URL = API_URL + "streamer/";
const API_LIVE_URL = API_URL + "live/";
export const fetchStreamerPseudoList = async () => {
    const response = await fetch(API_STREAMER_URL + "pseudo/list");
    return await response.json();
};

export const fetchLiveThematiqueList = async () => {
    const response = await fetch(API_LIVE_URL + "thematique/list");
    return await response.json();
}

export const fetchLives = async (rq) => {
    const response = await fetch(API_LIVE_URL + "list/param" + rq);
    return await response.json();
};

export const fetchTodayAndUpcomingLives = async () => {
    const response = await fetch(API_LIVE_URL + "list/dateStart/greaterThanEquals");
    return await response.json();
}