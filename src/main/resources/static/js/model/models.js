
export class Pegi {
    key;
    label;
    description

    constructor(key, label, description) {
        this.key = key;
        this.label = label;
        this.description = description;
    }

    get key() { return this.key;}

    set key(key){ this.key = key;}

    get label(){ return this.label;}

    set label(label){ this.label = label;}

    get description(){ return this.description;}

    set description(description){ this.description = description;}

    stringify() { return JSON.stringify(this);}
}

export class Theme {
    key;
    label;
    description

    constructor(key, label, description) {
        this.key = key;
        this.label = label;
        this.description = description;
    }

    get key() { return this.key;}

    set key(key){ this.key = key;}

    get label(){ return this.label;}

    set label(label){ this.label = label;}

    get description(){ return this.description;}

    set description(description){ this.description = description;}

    stringify() { return JSON.stringify(this);}
}
export class Live {
    uuid;
    title;
    description;
    themes;
    pegi;
    streamerPseudo;
    dateStart;
    dateEnd;

    constructor() {
        this.uuid = "";
        this.streamerPseudo = "";
        this.themes = new Array();
    }

    set title(title) {
        this.title = title;
    }

    get title(){ return this.title;}

    set description(description){
        this.description = description;
    }

    get description(){ return this.description;}

    set themes(themes){
        this.themes = themes;
    }

    get themes(){ return this.themes;}

    set pegi(pegi){
        this.pegi = pegi;
    }

    get pegi(){ return this.pegi;}

    set dateStart(date){
        this.dateStart = date;
    }

    get dateStart(){ return this.dateStart;}

    set dateEnd(date){
        this.dateEnd = date;
    }

    get dateEnd(){ return this.dateEnd;}

    stringify() {
        return JSON.stringify(this);
    }
}

export class Rule {
    key;
    label;

    constructor(key, label) {
        this.key = key;
        this.label = label;
    }

    static parse(other){
        const rule = new Rule();
        rule.key = other.key;
        rule.label = other.label;
        return rule;
    }

    stringify() {
        return JSON.stringify(this);
    }
}

export class Streamer {
    matricule;
    pseudo;
    age;
    rule;
    chaine;

    constructor() {
    }

    static parse(other) {
        const streamer = new Streamer();
        // streamer.matricule = other.matricule;
        streamer.pseudo = other.pseudo;
        streamer.age = other.age;
        streamer.chaine = other.chaine;
        streamer.rule = Rule.parse(other.rule);
        return streamer;
    }

    stringify() {
        return JSON.stringify(this);
    }
}