function exists(data) {
    if (typeof data !== typeof undefined && data !== false) {
        return false;
    }
    return true;
}