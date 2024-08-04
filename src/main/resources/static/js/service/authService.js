import {API_URL} from "./apiService.js"


export const API_AUTH_URL = API_URL + "auth";
export class AuthService {

    static AUTH_DATA_ITEM = "authData";

    static postRestPassword = async (resetPassword) => {
        const parameters = AuthService.httpHeader("POST");
        parameters.body = JSON.stringify(resetPassword);
        const response = await fetch(`${API_AUTH_URL}/reset-password`, parameters);
        return await response.json();
    }

    static postSignup = async (streamer) => {
        const parameters = AuthService.httpHeader("POST");
        parameters.body = JSON.stringify(streamer);
        const response = await fetch(`${API_AUTH_URL}/signup`, parameters);
        return await response.json();
    }

    static postSignin = async (authData) => {
        const parameters = AuthService.httpHeader("POST");
        parameters.body = JSON.stringify(authData);
        const response = await fetch(`${API_AUTH_URL}/signin`, parameters);
        return await response.json();
    };

    static fetchForgotPassword = async(email) => {
        const response = await fetch(`${API_AUTH_URL}/forgot-password/${email}`);
        return await response.json();
    }

    static setAuthData(authData) {
        localStorage.setItem(AuthService.AUTH_DATA_ITEM, JSON.stringify(authData));
    }

    static getAuthData() {
        const authDataStr = localStorage.getItem(AuthService.AUTH_DATA_ITEM);
        return authDataStr ? JSON.parse(authDataStr) : null;
    }

    static getStreamer() {
        const authData = AuthService.getAuthData();
        return authData ? authData.streamerDTO : null;
    }
    static getRule() {
        const streamer = AuthService.getStreamer();
        return streamer ? streamer.rule.label : null;
    }

    static getToken() {
        const authData = AuthService.getAuthData();
        return authData ? authData.token : null;
    }

    static logout() {
        AuthService.setAuthData(null);
    }

    static isConnected() {
        const token = AuthService.getToken();
        return token != null && token.length > 1;
    }

    static disconnect() {
        AuthService.setAuthData(null);
    }

    static httpHeader(method, withAuthorization = false) {
        const parameters = {
            method: method,
            headers : {
                'Content-Type': 'application/json'
            }
        };
        if(withAuthorization) parameters.headers.Authorization = `Bearer ${this.getToken()}`;
        console.log(parameters);
        return parameters;
     }

     static fetchIsEmailAndPseudoUniques = async (email, pseudo) => {
         const response = await fetch(`${API_AUTH_URL}/unique/${email}/${pseudo}`);
         return await response.json();
     }

     static isAdmin() {
        return AuthService.getRule() === "admin";
     }

     static isStreamer() {
        return AuthService.getRule() === "streamer";
    }

}