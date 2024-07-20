import {API_URL} from "./apiService.js";


const API_STREAMER_URL = API_URL + "streamer";

export class StreamerService {
    static postCreateStreamer = async (streamer) => {
        const response = await fetch(`${API_STREAMER_URL}/`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(streamer)
        });
        return await response.json();
    }

    static patchUpdateStreamer = async (streamer) => {
        const response = await fetch(`${API_STREAMER_URL}/`, {
            method: "PATCH",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(streamer)
        });
        return await response.json();
    }
    static fetchStreamerPseudoList = async () => {
        const response = await fetch(API_STREAMER_URL + "pseudo/list");
        return await response.json();
    };

    static fetchStreamerList = async () => {
        const response = await fetch(`${API_STREAMER_URL}/list`);
        return await response.json();
    }

    static fetchRuleAndStatusList = async () => {

        const response = await fetch(`${API_STREAMER_URL}/rule-and-status-list`);
        return await response.json();
    };
}