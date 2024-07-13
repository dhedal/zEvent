
export class StreamerTable{
    parent;
    table;
    thead;
    streamerArray;
    tbody;
    constructor(parentId, streamerArray = new Array()) {
        this.parent = document.getElementById(parentId);
        this.table = document.createElement("table");
        this.parent.innerHTML = "";
        this.parent.appendChild(this.table);
        this.table.classList.add("table", "table-striped",  "table-sm");

        this.thead = this.createAndGetTHead();
        this.table.appendChild(this.thead);

        this.streamerArray = streamerArray;
        this.tbody = this.createAndGetTBody(this.streamerArray);
        this.table.appendChild(this.tbody);

    }

    createAndGetTHead() {
        const thead = document.createElement("thead");
        thead.innerHTML = `
        <tr>
            <th scope="col">#</th>
            <th scope="col">Nom</th>
            <th scope="col">Prénom</th>
            <th scope="col">Pseudo</th>
            <th scope="col">Email</th>
            <th scope="col">Date de naissance</th>
            <th scope="col">Chaine</th>
            <th scope="col">Role</th>
            <th scope="col">Status</th>
            <th scope="col">Modifier</th>
        </tr>
        `;
        return thead;
    }

    createAndGetTd(data) {
        const td = document.createElement("td");
        td.textContent = data;
        return td;
    }

    createAndGetTdCheckbox() {
        const td = document.createElement("td");
        td.innerHTML = '<input type="checkbox"/>';
        return td;
    }

    createAndGetTdButton() {
        const td = document.createElement("td");
        td.innerHTML = '<button class="btn btn-outline-warning">Modifier</button>';
        return td;
    }

    createAndGetTr(streamer) {
        const tr = document.createElement("tr");
        tr.appendChild(this.createAndGetTdCheckbox());
        tr.appendChild(this.createAndGetTd(streamer.firstName));
        tr.appendChild(this.createAndGetTd(streamer.lastName));
        tr.appendChild(this.createAndGetTd(streamer.pseudo));
        tr.appendChild(this.createAndGetTd(streamer.email));
        tr.appendChild(this.createAndGetTd(streamer.birthDate));
        tr.appendChild(this.createAndGetTd(streamer.chaine));
        tr.appendChild(this.createAndGetTd(streamer.rule.label));
        tr.appendChild(this.createAndGetTd(streamer.status.label));
        tr.appendChild(this.createAndGetTdButton());
        return tr;
    }

    createAndGetTBody(streamers) {
        const tbody = document.createElement("tbody");
        streamers.forEach(streamer => {
            tbody.appendChild(this.createAndGetTr(streamer));
        });
        return tbody;
    }


    clear(){
        this.tbody.innerHTML = "";
    }

    addStreamer(streamer) {
        this.tbody.appendChild(this.createAndGetTr(streamer));
    }

}