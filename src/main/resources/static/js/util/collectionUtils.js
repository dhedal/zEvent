export class CollectionUtils {

    static isArrayNullOrEmpty(array) {
        if(array == null) return true;
        return Array.isArray(array) && array.length === 0;
    }

    static isMapNullOrEmpty(map) {
        if(map == null) return true;
        return map.size === 0;

    }
}