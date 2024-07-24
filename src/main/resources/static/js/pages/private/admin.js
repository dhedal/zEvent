import {StreamerForm, StreamerModal, StreamerTable} from "../../component/adminComponent.js";
import {StreamerService} from "../../service/streamerService.js";

let dataRuleList;
let dataStatusList;
let streamerArray;
const fetchDatas = async () => {
    return Promise.all([
        StreamerService.fetchRuleAndStatusList().then(response => {
            dataRuleList = response.rules;
            dataStatusList = response.status;
        }),
        StreamerService.fetchStreamerList().then(response => {
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
            if(streamerData.uuid == null || streamerData.uuid.length == 0) {
                console.log("create");
                StreamerService.postCreateStreamer(streamerData).then(streamer => {

                    if(streamer != null && streamer.uuid != null && streamer.uuid.length > 0){
                        streamerTable.addStreamer(streamer);
                        streamerModal.close();
                    }
                });
            }
            else {
                console.log("update");
                StreamerService.patchUpdateStreamer(streamerData).then(streamer => {

                    if(streamer != null && streamer.uuid != null && streamer.uuid.length > 0){
                        streamerTable.addStreamer(streamer);
                        streamerModal.close();
                    }
                });
            }

        });
    });
})();