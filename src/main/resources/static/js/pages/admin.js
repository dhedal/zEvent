import {StreamerForm, StreamerModal, StreamerTable} from "../component/adminComponent.js";
import {ApiService} from "../service/apiService.js";

let dataRuleList;
let dataStatusList;
let streamerArray;
const fetchDatas = async () => {
    return Promise.all([
        ApiService.fetchRuleAndStatusList().then(response => {
            dataRuleList = response.rules;
            dataStatusList = response.status;
        }),
        ApiService.fetchStreamerList().then(response => {
            streamerArray = response;
        })
    ]);
};

(function() {

    fetchDatas().then(() => {
        const streamerTable = new StreamerTable("streamerTableContainerId", streamerArray);
        const form = new StreamerForm("streamerForm");
        form.buildForm(dataRuleList, dataStatusList);
        const streamerModal = new StreamerModal('streamerModal', streamerTable, form);

        form.form.addEventListener("data-streamer-submit", event => {
            const streamerData = event.detail.data;
            console.log(streamerData);
            ApiService.postSaveStreamer(streamerData).then(streamer => {
                streamerTable.addStreamer(streamer);
            });
        });
    });
})();