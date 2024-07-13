import {StreamerTable} from "../component/adminComponent.js";
import {ApiService} from "../service/apiService.js";

(function() {

    ApiService.fetchStreamerList().then(response => {
        const streamerArray = response;
        console.log(streamerArray);
        const streamerTable = new StreamerTable("streamerTableContainerId", streamerArray);
    });


})();