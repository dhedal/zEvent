import {API_URL} from "./apiService.js"

export const API_AUTH_URL = API_URL + "auth";
export class AuthService {

    static postSignup = async (streamer) => {
        const response = await fetch(`${API_AUTH_URL}/signup`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(streamer)
        });
        return await response.json();
    }

}