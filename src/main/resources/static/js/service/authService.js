import {API_URL} from "./apiService.js"

export const API_AUTH_URL = API_URL + "auth";
export class AuthService {

    static postSignup = async (streamer) => {
        const parameters = AuthService.fetchParameter("POST");
        parameters.body = JSON.stringify(streamer);
        const response = await fetch(`${API_AUTH_URL}/signup`, parameters);
        return await response.json();
    }

    static setToken(token) {
        localStorage.setItem("jwt", token);
    }

    static getToken() {
        return localStorage.getItem("jwt");
    }

    static setRule(rule) {
        return localStorage.setItem("rule", rule);
    }

    static getRule() {
        return localStorage.getItem("rule");
    }

    static logout() {
        AuthService.setToken(null);
        AuthService.setRule(null);
    }

    static signin(token, rule) {
        AuthService.setToken(token);
        AuthService.setRule(rule);
    }

    static isConnected() {
        const token = AuthService.getToken();
        return token != null && token.length > 1;
    }

    static fetchParameter(method = "POST", withAuthorization = false) {
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



}