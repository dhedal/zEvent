export class DateUtils {
    date;
    constructor(date = null) {
        this.date = date == null || date === "" ? new Date() : date;
    }

    getDate() { return this.date;}
    getHoursToString() {
        return this.date.getHours().toString();
    }

    getMinutesToString() { return this.date.getMinutes() + "";}

    getDateFormat_YYYY_MM_DD(limiter = "-") {
        const month = this.date.getMonth() + 1;
        const day = this.date.getDate();
        return Array
            .of(this.date.getFullYear(),
                month < 10 ? "0" + month : "" + month,
                day < 10 ? "0" + day : "" + day)
            .join(limiter);
    }

    getDateFormat_DD_MM_YYYY(limiter = "/") {
        const month = this.date.getMonth() + 1;
        const day = this.date.getDate();
        return Array
            .of(day < 10 ? "0" + day : "" + day,
                month < 10 ? "0" + month : "" + month,
                this.date.getFullYear())
            .join(limiter);
    }

    greaterThanOrEquals(date) {
        return this.date >= date;
    }

    static buildDate(dateString) {
        return new DateUtils(new Date(dateString));
    }

    static parseToDate(date, hours, minutes) {
        const format = `${date}T${DateUtils.formatHoursOrMinutes(hours)}:${DateUtils.formatHoursOrMinutes(minutes)}:00`;
        return new Date(format);
    }

    static formatHoursOrMinutes(data) {
        data += "";
        return data.length < 2 ? "0" + data : data;
    }

    static getDateNowByAge(age) {
        const today = new Date();
        const birthDate = new Date();
        birthDate.setFullYear(today.getFullYear() - age);
        return new DateUtils(birthDate);
    }

}