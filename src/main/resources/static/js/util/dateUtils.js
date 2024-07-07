export class DateUtils {
    date;
    constructor(date) {
        this.date = date;
    }

    getDate() { return this.date;}
    getHoursToString() {
        return this.date.getHours().toString();
    }

    getMinutesToString() { return this.date.getMinutes() + "";}

    getDateFormat_YYYY_MM_DD(limiter = "-") {
        const month = this.date.getMonth() + 1;
        return Array
            .of(this.date.getFullYear(), month < 10 ? "0" + month : "" + month, this.date.getDate())
            .join(limiter);
    }

    getDateFormat_DD_MM_YYYY(limiter = "/") {
        const month = this.date.getMonth() + 1;
        return Array
            .of(this.date.getDate(), month < 10 ? "0" + month : "" + month, this.date.getFullYear())
            .join(limiter);
    }

    static buildDate(dateString) {
        return new DateUtils(new Date(dateString));
    }

}